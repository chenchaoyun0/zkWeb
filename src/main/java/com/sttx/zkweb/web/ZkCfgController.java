package com.sttx.zkweb.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sttx.zkweb.model.ZkConfig;
import com.sttx.zkweb.service.ZkCfgManager;
import com.sttx.zkweb.service.impl.ZkManagerImpl;
import com.sttx.zkweb.util.ZkCache;

@Controller
@RequestMapping("/zkcfg")
public class ZkCfgController {

  private static final Logger log = LoggerFactory.getLogger(ZkCfgController.class);
  @Autowired
  private ZkCfgManager zkCfgManager;

  @RequestMapping(value = "/queryZkCfg")
  public @ResponseBody Map<String, Object> queryZkCfg(@RequestParam(required = false) int page,
    @RequestParam(required = false) int rows) {
    try {
      log.info(new Date() + "");
      Map<String, Object> _map = new HashMap<String, Object>();
      List<ZkConfig> list = zkCfgManager.query(page, rows);
      _map.put("rows", list);
      _map.put("total", zkCfgManager.count());
      return _map;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  @RequestMapping(value = "/addZkCfg", produces = "text/html;charset=UTF-8")
  public @ResponseBody String addZkCfg(ZkConfig zkConfig) {

    try {
      String zkId = UUID.randomUUID().toString().replaceAll("-", "");
      zkConfig.setZkId(zkId);
      zkCfgManager.add(zkConfig);
      ZkManagerImpl zk = new ZkManagerImpl(zkConfig.getZkConnectStr(), Integer.parseInt(zkConfig.getZkSessionTimeOut()),
        zkConfig.getZkUserName(), zkConfig.getZkUserPwd());
      ZkCache.put(zkConfig.getZkId(), zk);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return "添加失败";
    }
    return "添加成功";
  }

  @RequestMapping(value = "/queryZkCfgById")
  public @ResponseBody ZkConfig queryZkCfg(@RequestParam("zkId") String zkId) {

    try {
      return zkCfgManager.findById(zkId);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  @RequestMapping(value = "/updateZkCfg", produces = "text/html;charset=UTF-8")
  public @ResponseBody String updateZkCfg(ZkConfig zkConfig) {

    try {
      zkCfgManager.update(zkConfig);
      ZkManagerImpl zk = new ZkManagerImpl(zkConfig.getZkConnectStr(), Integer.parseInt(zkConfig.getZkSessionTimeOut()),
        zkConfig.getZkUserName(), zkConfig.getZkUserPwd());
      ZkCache.put(zkConfig.getZkId(), zk);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return "保存失败";
    }
    return "保存成功";
  }

  @RequestMapping(value = "/delZkCfg", produces = "text/html;charset=UTF-8")
  public @ResponseBody String delZkCfg(@RequestParam("zkId") String zkId) {

    try {
      zkCfgManager.delete(zkId);
      ZkManagerImpl zkManager = ZkCache.remove(zkId);
      /**
       * 删除后断开连接
       */
      zkManager.disconnect();
      log.info("" + zkManager);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return "删除失败";
    }
    return "删除成功";
  }
}
