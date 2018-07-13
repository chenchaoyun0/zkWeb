package com.sttx.zkweb.service;

import java.util.List;

import com.sttx.zkweb.model.ZkConfig;

/**
 * 
 * @ClassName: ZkCfgManager
 * @Description: zookeeper 配置 接口
 * @author: chenchaoyun0
 * @date: 2016年8月21日 下午2:33:27
 */
public interface ZkCfgManager {

    /**
     * 
     * @Title: add
     * @Description: 添加一个zookeeper配置
     * @param zkConfig
     *            zookeeper配置对象
     * @throws Exception
     * @return: void
     */
    public void add(ZkConfig zkConfig) throws Exception;

    /**
     * 
     * @Title: query
     * @Description: 查询zookeeper配置列表，暂未调用，调用的是以下分页方法
     * @return
     * @throws Exception
     * @return: List<ZkConfig>
     */
    public List<ZkConfig> query() throws Exception;

    /**
     * 
     * @Title: query
     * @Description: 查询zookeeper配置列表分页
     * @param page
     * @param rows
     * @return
     * @throws Exception
     * @return: List<ZkConfig>
     */
    public List<ZkConfig> query(int page, int rows) throws Exception;

    /**
     * 
     * @Title: update
     * @Description: 更新zookeeper配置信息
     * @param zkConfig
     * @throws Exception
     * @return: void
     */
    public void update(ZkConfig zkConfig) throws Exception;

    /**
     * 
     * @Title: delete
     * @Description: 根据zkId删除该配置
     * @param zkId
     * @throws Exception
     * @return: void
     */
    public void delete(String zkId) throws Exception;

    /**
     * 
     * @Title: findById
     * @Description: zkId查找
     * @param zkId
     * @return
     * @throws Exception
     * @return: ZkConfig
     */
    public ZkConfig findById(String zkId) throws Exception;

    /**
     * 
     * @Title: count
     * @Description: 配置数量
     * @return
     * @throws Exception
     * @return: int
     */
    public int count() throws Exception;

    /* 初始化数据库表 */
    static String initSql = "CREATE TABLE IF NOT EXISTS T_ZKCONFIG(ZKID VARCHAR(200) PRIMARY KEY, ZKDESCRIPTION VARCHAR(100), ZKCONNECTSTR VARCHAR(100), ZKSESSIONTIMEOUT VARCHAR(100),ZKUSERNAME VARCHAR(100),ZKUSERPWD VARCHAR(200))";
    static String initAdminSql = "CREATE TABLE IF NOT EXISTS T_ADMIN(AID VARCHAR(200) PRIMARY KEY, ADMINNAME VARCHAR(100), ADMINPWD VARCHAR(100))";
    /* 删除表 */
    static String dropSqlAdmin = "TRUNCATE TABLE T_ADMIN";
}
