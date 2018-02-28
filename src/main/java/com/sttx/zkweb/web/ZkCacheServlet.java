package com.sttx.zkweb.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sttx.zkweb.admin.dao.LoginDaoFactory;
import com.sttx.zkweb.model.Admin;
import com.sttx.zkweb.model.ZkConfig;
import com.sttx.zkweb.service.ZkCfgManager;
import com.sttx.zkweb.service.impl.ZkManagerImpl;
import com.sttx.zkweb.util.PropertiesUtil;
import com.sttx.zkweb.util.ZkCache;
import com.sttx.zkweb.util.ZkCfgFactory;
import com.sttx.zkweb.util.ZkException;

import cn.itcast.commons.CommonUtils;

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
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            for (ZkConfig zkConfig : ZkCfgFactory.getZkCfgManager().query()) {
                log.info("ID : {},CONNECTSTR : {},SESSIONTIMEOUT : {}", new Object[] { zkConfig.getZkId(),
                        zkConfig.getZkConnectStr(), zkConfig.getZkSessionTimeOut() });
                ZkManagerImpl zk = new ZkManagerImpl(zkConfig.getZkConnectStr(),
                        Integer.parseInt(zkConfig.getZkSessionTimeOut()), zkConfig.getZkUserName(),
                        zkConfig.getZkUserPwd());
                ZkCache.put(zkConfig.getZkId(), zk);
            }
        } catch (NumberFormatException e) {
            log.error("数字格式化异常 :error{}", e.getMessage(), e);
        } catch (ZkException e) {
            log.error("ZkException error:{}", e.getMessage(), e);
        }

        for (String key : ZkCache.getZkCache().keySet()) {
            log.info("key : {} , zk : {}", key, ZkCache.get(key));
        }
    }

    @Override
    public void init() throws ServletException {
        /**
         * 初始化数据库
         */
        ZkCfgManager zkCfgManager = ZkCfgFactory.getZkCfgManager();
        try {
            /**
             * 这里会初始化t_admin，t_zkconfig两张表 并且截断admin表，即清空表中数据
             */
            zkCfgManager.init();
            /**
             * 判断表中是否存在数据
             */
            if (LoginDaoFactory.getLoginDaoImpl().queryAdminCount() == 0) {
                String adminName = PropertiesUtil.getFilePath("admin.properties", "adminName");
                String adminPwd = PropertiesUtil.getFilePath("admin.properties", "adminPwd");
                Admin admin = new Admin();
                admin.setaId(CommonUtils.uuid());
                admin.setAdminName(adminName);
                admin.setAdminPwd(adminPwd);
                LoginDaoFactory.getLoginDaoImpl().addAdmin(admin);
            }
        } catch (ZkException e) {
            log.error(" init admin ZkException error:{}", e.getMessage(), e);
        }
        /**
         * 加载zk
         */
        ZkCache.init(ZkCfgFactory.getZkCfgManager());
        log.info("init {} zk instance", ZkCache.size());
        super.init();
    }

}
