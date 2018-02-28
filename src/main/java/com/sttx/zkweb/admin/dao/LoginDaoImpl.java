package com.sttx.zkweb.admin.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sttx.zkweb.model.Admin;
import com.sttx.zkweb.util.DBUtil;
import com.sttx.zkweb.util.ZkException;

/**
 * 
 * @ClassName: LoginDaoImpl
 * @Description: 用户登录dao层
 * @author: chenchaoyun0
 * @date: 2016年8月20日 下午10:47:08
 */
public class LoginDaoImpl {

  private static final Logger logger = LoggerFactory.getLogger(LoginDaoImpl.class);

    private QueryRunner run = new QueryRunner(DBUtil.getDataSource());

    /**
     * 
     * @Title: adminLogin
     * @Description: admin登录
     * @param AdminName
     *            用户名
     * @return
     * @return: Admin 管理员实例
     */
    public Admin adminLogin(String AdminName) {
        try {
            logger.info("adminLogin info:adminName={}" + AdminName);
            return run.query("SELECT * FROM T_ADMIN WHERE ADMINNAME=?", new BeanHandler<Admin>(Admin.class), AdminName);
        } catch (SQLException e) {
            logger.error("adminLogin errorMessage={}" + e.getMessage(), e);
            throw new RuntimeException("登录有误", e);
        }
    }

    /**
     * 
     * @Title: updateAdminPwd
     * @Description: 修改admin密码
     * @param admin
     *            admin实例
     * @return: void
     */
    public void updateAdminPwd(Admin admin) {
        try {
            logger.info("updateAdminPwd info:{}" + admin.getAdminName());
            run.update("UPDATE T_ADMIN SET ADMINPWD=? WHERE AID=?", admin.getAdminPwd(), admin.getaId());
        } catch (SQLException e) {
            logger.error("updateAdminPwd error{}" + e.getMessage(), e);
            throw new RuntimeException("updateAdminPwd", e);
        }
    }

    public void addAdmin(Admin admin) throws ZkException {
        try {
            logger.info("addAdmin info adminName:{}" + admin.getAdminName());
            run.update("INSERT INTO T_ADMIN VALUES(?,?,?)", admin.getaId(), admin.getAdminName(), admin.getAdminPwd());
        } catch (SQLException e) {
            logger.error("addAdmin error{}" + e.getMessage(), e);
            throw new ZkException("addAdmin 初始化管理员异常");
        }
    }

    public int queryAdminCount() throws ZkException {
        try {
            logger.info("queryAdminCount");
            Number num = (Number) run.query("SELECT COUNT(AID) FROM T_ADMIN", new ScalarHandler());
            return num.intValue();
        } catch (SQLException e) {
            logger.error("queryAdminCount error{}" + e.getMessage(), e);
            throw new ZkException("queryAdminCount 初始化管理员异常");
        }
    }
}
