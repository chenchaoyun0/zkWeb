package com.sttx.zkweb.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sttx.zkweb.model.Tree;
import com.sttx.zkweb.model.TreeRoot;
import com.sttx.zkweb.model.ZkNodeData;
import com.sttx.zkweb.service.impl.ZkManagerImpl;
import com.sttx.zkweb.util.Const;
import com.sttx.zkweb.util.ZkCache;

@Controller
@RequestMapping("/zk")
public class ZkController {

  private static final Logger log = LoggerFactory.getLogger(ZkController.class);

  @RequestMapping(value = "/queryZnodeInfo")
  public String queryzNodeInfo(@RequestParam(required = false) String path, Model model,
    @RequestParam(required = true) String cacheId, HttpServletResponse response, String username, String password,
    HttpServletRequest request) throws IOException {
    try {
      path = URLDecoder.decode(path, Const.ENCODING);
      log.info("queryzNodeInfo : " + path);
      if (path != null) {
        ZkNodeData zkNodeData = ZkCache.get(cacheId).getData(path);
        model.addAttribute("zkNodeData", zkNodeData);
        model.addAttribute("path", path);
        model.addAttribute("cacheId", cacheId);
      }
    } catch (Exception e) {
      model.addAttribute("msg", e.getMessage());
      model.addAttribute("cacheId", cacheId);
      model.addAttribute("path", path);
      return "addAuth";
    }
    return "info";
  }

  @RequestMapping(value = "/queryNodeByAuth")
  public String queryNodeByAuth(@RequestParam(required = true) String cacheId,
    @RequestParam(required = true) String path, @RequestParam(required = true) String username,
    @RequestParam(required = true) String password) {
    ZkManagerImpl zkManager = ZkCache.get(cacheId);
    zkManager.addAuth(cacheId, path, username, password);
    return "redirect:/operation/home";
  }

  @RequestMapping(value = "/queryZnode")
  public @ResponseBody List<Tree> query(@RequestParam(required = false) String id,
    @RequestParam(required = false) String path, @RequestParam(required = false) String cacheId,
    HttpServletResponse response) throws IOException {
    log.info("id : {}", id);
    log.info("path : {}", path);
    log.info("cacheId : {}", cacheId);

    TreeRoot root = new TreeRoot();

    if (path == null) {

    } else if ("/".equals(path)) {
      root.remove(0);
      ZkManagerImpl zkManager = ZkCache.get(cacheId);
      List<String> pathList = zkManager.getChildren(null);
      log.info("list {}", pathList);
      for (String p : pathList) {
        Map<String, String> atr = new HashMap<String, String>();
        atr.put("path", "/" + p);
        Tree tree = new Tree(0, p, Tree.STATE_CLOSED, null, atr);
        root.add(tree);
      }
    } else {
      root.remove(0);
      try {
        path = URLDecoder.decode(path, Const.ENCODING);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      List<String> pathList = null;
      try {
        pathList = ZkCache.get(cacheId).getChildren(path);
      } catch (Exception e) {
        log.info(e.getMessage());
        return root;
      }
      for (String p : pathList) {
        Map<String, String> atr = new HashMap<String, String>();
        atr.put("path", path + "/" + p);
        Tree tree = new Tree(0, p, Tree.STATE_CLOSED, null, atr);
        root.add(tree);
      }
    }

    return root;
  }

  @RequestMapping(value = "/saveData", produces = "text/html;charset=UTF-8")
  public @ResponseBody String saveData(@RequestParam() String path, @RequestParam() String data,
    @RequestParam(required = true) String cacheId) {

    try {
      log.info("data:{}", data);
      return ZkCache.get(cacheId).setData(path, data.trim()) == true ? "保存成功" : "保存失败";
    } catch (Exception e) {
      log.info("Error : {}", e.getMessage());
      e.printStackTrace();
      return "保存失败! Error : " + e.getMessage();
    }

  }

  @RequestMapping(value = "/createNode", produces = "text/html;charset=UTF-8")
  public @ResponseBody String createNode(@RequestParam() String path, @RequestParam() String nodeName,
    @RequestParam(required = true) String cacheId) {

    try {
      log.info("path:{}", path);
      log.info("nodeName:{}", nodeName);
      return ZkCache.get(cacheId).createNode(path, nodeName, "") == true ? "保存成功" : "保存失败";
    } catch (Exception e) {
      log.info("Error : {}", e.getMessage());
      e.printStackTrace();
      return "保存失败! Error : " + e.getMessage();
    }

  }

  @RequestMapping(value = "/existChildPath", produces = "text/html;charset=UTF-8")
  public @ResponseBody String existChildPath(@RequestParam() String path, @RequestParam() String nodeName,
    @RequestParam(required = true) String cacheId) {
    try {
      log.info("path:{}", path);
      log.info("nodeName:{}", nodeName);
      List<String> children = ZkCache.get(cacheId).getChildren(path);
      if (children.contains(nodeName)) {
        return "true";
      } else {
        return "false";
      }
    } catch (Exception e) {
      log.info("Error : {}", e.getMessage());
      e.printStackTrace();
      return "保存失败! Error : " + e.getMessage();
    }

  }

  @RequestMapping(value = "/createNodeOfDegist", produces = "text/html;charset=UTF-8")
  public @ResponseBody String createNodeOfDegist(@RequestParam() String path, @RequestParam() String nodeName,
    @RequestParam() String nodeData, @RequestParam() String scheme, @RequestParam() String nodeUser,
    @RequestParam() String nodePassword, @RequestParam(required = true) String cacheId, HttpServletRequest request) {
    /**
     * 得到多选框的权限数组,并且转换成int数组
     */
    String[] permsStr = request.getParameterValues("nodePerms[]");
    int[] perms = new int[permsStr.length];
    for (int i = 0; i < permsStr.length; i++) {
      perms[i] = Integer.parseInt(permsStr[i]);
    }
    try {
      log.info("path:{}", path);
      log.info("nodeName:{}", nodeName);
      return ZkCache.get(cacheId).createNodeOfScheme(path, nodeName, nodeData, scheme, nodeUser, nodePassword, perms,
        cacheId) == true ? "保存成功" : "保存失败";
    } catch (Exception e) {
      log.info("Error : {}", e.getMessage());
      e.printStackTrace();
      return "保存失败! Error : " + e.getMessage();
    }

  }

  @RequestMapping(value = "/deleteNode", produces = "text/html;charset=UTF-8")
  public @ResponseBody String deleteNode(@RequestParam() String path, @RequestParam(required = true) String cacheId) {
    try {
      log.info("path:{}", path);
      return ZkCache.get(cacheId).deleteNode(path) == true ? "删除成功" : "删除失败";
    } catch (Exception e) {
      log.info("Error : {}", e.getMessage());
      return "删除失败! Error : " + e.getMessage();
    }

  }

  @RequestMapping(value = "/addNodeAcl", produces = "text/html;charset=UTF-8")
  public @ResponseBody String addNodeAcl(String path, String scheme, String adminName, String adminPwd, String nodeUser,
    String nodePassword, @RequestParam(required = true) String cacheId, HttpServletRequest request) {
    try {
      log.info("path:{}", path);
      String[] permsStr = request.getParameterValues("nodePerms[]");
      int[] perms = new int[permsStr.length];
      for (int i = 0; i < permsStr.length; i++) {
        perms[i] = Integer.parseInt(permsStr[i]);
      }

      ZkCache.get(cacheId).addNodeAcl(path, scheme, adminName, adminPwd, nodeUser, nodePassword, cacheId, perms);

      return "添加用户成功";
    } catch (Exception e) {
      log.info("Error : {}", e.getMessage());
      return "添加节点ACL失败! Error : " + e.getMessage();
    }
  }

  @RequestMapping("inputAuth")
  public String inputAuth() {

    return "addAuth";
  }

  @RequestMapping(value = "/upload", produces = "text/html;charset=UTF-8")
  public String importFile(HttpServletRequest request) throws Exception {
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
    MultipartFile file = multipartRequest.getFile("file");
    InputStream input = file.getInputStream();
    BufferedReader br = new BufferedReader(new InputStreamReader(input, "utf-8"));
    String cacheId = request.getParameter("id");
    ZkManagerImpl zk = ZkCache.get(cacheId);
    zk.importdata(br);
    return "redirect:/operation/home";
  }
}
