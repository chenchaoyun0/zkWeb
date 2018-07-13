package com.sttx.zkweb.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sttx.zkweb.model.ZkConfig;
import com.sttx.zkweb.service.ZkCfgManager;
import com.sttx.zkweb.service.impl.ZkManagerImpl;
import com.sttx.zkweb.util.SpringUtils;
import com.sttx.zkweb.util.ZkCache;

public class ZkCacheServlet extends HttpServlet {

  private static final Logger log = LoggerFactory.getLogger(ZkCacheServlet.class);

  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   */
  public ZkCacheServlet() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      ZkCfgManager zkCfgManager = SpringUtils.getBean(ZkCfgManager.class);
      for (ZkConfig zkConfig : zkCfgManager.query()) {
        log.info("ID : {},CONNECTSTR : {},SESSIONTIMEOUT : {}",
          new Object[] {zkConfig.getZkId(), zkConfig.getZkConnectStr(), zkConfig.getZkSessionTimeOut()});
        ZkManagerImpl zk = new ZkManagerImpl(zkConfig.getZkConnectStr(),
          Integer.parseInt(zkConfig.getZkSessionTimeOut()), zkConfig.getZkUserName(), zkConfig.getZkUserPwd());
        ZkCache.put(zkConfig.getZkId(), zk);
      }
    } catch (NumberFormatException e) {
      log.error("数字格式化异常 :error{}", e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception error:{}", e.getMessage(), e);
    }

    for (String key : ZkCache.getZkCache().keySet()) {
      log.info("key : {} , zk : {}", key, ZkCache.get(key));
    }
  }

  @Override
  public void init() throws ServletException {
    super.init();
  }

}
