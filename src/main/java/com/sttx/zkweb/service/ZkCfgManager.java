package com.sttx.zkweb.service;

import java.util.List;

import com.sttx.zkweb.model.ZkConfig;
import com.sttx.zkweb.util.ZkException;

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
     * @Title: init
     * @Description: 初始化数据库表结构
     * @throws ZkException
     * @return: void
     */
    public void init() throws ZkException;

    /**
     * 
     * @Title: dropTable
     * @Description: 删除表
     * @throws ZkException
     * @return: void
     */
    public void dropTable() throws ZkException;

    /**
     * 
     * @Title: add
     * @Description: 添加一个zookeeper配置
     * @param zkConfig
     *            zookeeper配置对象
     * @throws ZkException
     * @return: void
     */
    public void add(ZkConfig zkConfig) throws ZkException;

    /**
     * 
     * @Title: query
     * @Description: 查询zookeeper配置列表，暂未调用，调用的是以下分页方法
     * @return
     * @throws ZkException
     * @return: List<ZkConfig>
     */
    public List<ZkConfig> query() throws ZkException;

    /**
     * 
     * @Title: query
     * @Description: 查询zookeeper配置列表分页
     * @param page
     * @param rows
     * @return
     * @throws ZkException
     * @return: List<ZkConfig>
     */
    public List<ZkConfig> query(int page, int rows) throws ZkException;

    /**
     * 
     * @Title: update
     * @Description: 更新zookeeper配置信息
     * @param zkConfig
     * @throws ZkException
     * @return: void
     */
    public void update(ZkConfig zkConfig) throws ZkException;

    /**
     * 
     * @Title: delete
     * @Description: 根据zkId删除该配置
     * @param zkId
     * @throws ZkException
     * @return: void
     */
    public void delete(String zkId) throws ZkException;

    /**
     * 
     * @Title: findById
     * @Description: zkId查找
     * @param zkId
     * @return
     * @throws ZkException
     * @return: ZkConfig
     */
    public ZkConfig findById(String zkId) throws ZkException;

    /**
     * 
     * @Title: count
     * @Description: 配置数量
     * @return
     * @throws ZkException
     * @return: int
     */
    public int count() throws ZkException;

    /* 初始化数据库表 */
    static String initSql = "CREATE TABLE IF NOT EXISTS T_ZKCONFIG(ZKID VARCHAR(200) PRIMARY KEY, ZKDESCRIPTION VARCHAR(100), ZKCONNECTSTR VARCHAR(100), ZKSESSIONTIMEOUT VARCHAR(100),ZKUSERNAME VARCHAR(100),ZKUSERPWD VARCHAR(200))";
    static String initAdminSql = "CREATE TABLE IF NOT EXISTS T_ADMIN(AID VARCHAR(200) PRIMARY KEY, ADMINNAME VARCHAR(100), ADMINPWD VARCHAR(100))";
    /* 删除表 */
    static String dropSqlAdmin = "TRUNCATE TABLE T_ADMIN";
}
