package com.sttx.zkweb.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sttx.zkweb.model.ZkConfig;
import com.sttx.zkweb.service.ZkCfgManager;
import com.sttx.zkweb.util.ZkException;

import cn.itcast.jdbc.TxQueryRunner;

/**
 * 
 * @ClassName: ZkCfgManagerImpl
 * @Description: zookeeper 配置实现类
 * @author: chenchaoyun0
 * @date: 2016年8月21日 下午2:35:06
 */
public class ZkCfgManagerImpl implements ZkCfgManager {

  private static final Logger logger = LoggerFactory.getLogger(ZkCfgManagerImpl.class);
    private QueryRunner run = new TxQueryRunner();

    public void init() throws ZkException {
        try {
            logger.info("initSql,init 初始化表 :1= {}2={}" + ZkCfgManager.initSql, ZkCfgManager.initAdminSql);
            run.update(ZkCfgManager.initSql);
            run.update(ZkCfgManager.initAdminSql);
        } catch (SQLException e) {
            logger.error("init zkCfg table error : {}", e.getMessage(), e);
            throw new ZkException("init table exception");
        }
    }

    public void add(ZkConfig zkConfig) throws ZkException {
        try {
            logger.info("add zkConfig:{}" + zkConfig);
            run.update("INSERT INTO T_ZKCONFIG VALUES(?,?,?,?,?,?)", zkConfig.getZkId(), zkConfig.getZkDescription(),
                    zkConfig.getZkConnectStr(), zkConfig.getZkSessionTimeOut(), zkConfig.getZkUserName(),
                    zkConfig.getZkUserPwd());
        } catch (SQLException e) {
            logger.error("add zkCfg error : {}", e.getMessage(), e);
            throw new ZkException("insert zkconfig exception");
        }
    }

    public List<ZkConfig> query() throws ZkException {
        try {
            logger.info("query List<ZkConfig>.");
            return run.query(
                    "SELECT ZKID,ZKDESCRIPTION,ZKCONNECTSTR,ZKSESSIONTIMEOUT,ZKUSERNAME,ZKUSERPWD FROM T_ZKCONFIG",
                    new BeanListHandler<ZkConfig>(ZkConfig.class));
        } catch (SQLException e) {
            logger.error("queryzkCfg error : {}", e.getMessage(), e);
            throw new ZkException("select zkconfig exception");
        }
    }

    public void update(ZkConfig zkConfig) throws ZkException {
        try {
            logger.info("update List<ZkConfig> : {}", zkConfig);
            run.update(
                    "UPDATE T_ZKCONFIG SET ZKDESCRIPTION=?,ZKCONNECTSTR=?,ZKSESSIONTIMEOUT=?,ZKUSERNAME=?,ZKUSERPWD=? WHERE ZKID=?",
                    zkConfig.getZkDescription(), zkConfig.getZkConnectStr(), zkConfig.getZkSessionTimeOut(),
                    zkConfig.getZkUserName(), zkConfig.getZkUserPwd(), zkConfig.getZkId());
        } catch (Exception e) {
            logger.error("update kCfg error : {}", e.getMessage(), e);
            throw new ZkException("update zkconfig exception");
        }
    }

    public void delete(String zkId) throws ZkException {
        try {
            logger.info("delete ZkConfig by zkId : {}", zkId);
            run.update("DELETE from T_ZKCONFIG WHERE ZKID=?", zkId);
        } catch (Exception e) {
            logger.error("delete id={} zkCfg error : {}", zkId, e.getMessage(), e);
            throw new ZkException("delete zkconfig exception");
        }
    }

    public ZkConfig findById(String zkId) throws ZkException {
        try {
            logger.info("find ZkConfig by zkId : {}", zkId);
            return run.query("SELECT * FROM T_ZKCONFIG WHERE ZKID=?", new BeanHandler<ZkConfig>(ZkConfig.class), zkId);
        } catch (SQLException e) {
            logger.error("findById id={} zkCfg error : {}", zkId, e.getMessage(), e);
            throw new ZkException("SELECT zkconfig exception");
        }
    }

    public List<ZkConfig> query(int page, int rows) throws ZkException {
        try {
            logger.info("query ZkConfig pages : {},{}", page, rows);
            return run.query(
                    "SELECT ZKID,ZKDESCRIPTION,ZKCONNECTSTR,ZKSESSIONTIMEOUT,ZKUSERNAME FROM T_ZKCONFIG LIMIT ?,?",
                    new BeanListHandler<ZkConfig>(ZkConfig.class), (page - 1) * rows, rows);
        } catch (Exception e) {
            logger.error("query ZkConfig pages error : {}", e.getMessage(), e);
            throw new ZkException("SELECT page zkconfig exception");
        }
    }

    public int count() throws ZkException {
        try {
            logger.info("query ZkConfig count : {}");
            Number num = (Number) run.query("SELECT COUNT(ZKID) FROM T_ZKCONFIG", new ScalarHandler());
            return num.intValue();
        } catch (SQLException e) {
            logger.error("count id={} zkCfg error : {}", e.getMessage(), e);
            throw new ZkException("SELECT page zkconfig exception");
        }
    }

    public void dropTable() throws ZkException {
        try {
            logger.info("dropTable  : {}", ZkCfgManager.dropSqlAdmin);
            run.update(ZkCfgManager.dropSqlAdmin);
        } catch (Exception e) {
            logger.error("dropTable id={} zkCfg error : {}", e.getMessage(), e);
            throw new ZkException("dropTable exception");
        }
    }

}
