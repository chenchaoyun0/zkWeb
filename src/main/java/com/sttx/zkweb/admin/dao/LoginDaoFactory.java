package com.sttx.zkweb.admin.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: LoginDaoFactory
 * @Description: 获取Login Dao层连接db类实例
 * @author: chenchaoyun0
 * @date: 2016年8月20日 下午10:46:39
 */
public class LoginDaoFactory {
  private static final Logger logger = LoggerFactory.getLogger(LoginDaoFactory.class);

    public static LoginDaoImpl getLoginDaoImpl() {
        logger.info("LoginDaoFactory getLoginDaoImpl");
        return new LoginDaoImpl();
    }
}
