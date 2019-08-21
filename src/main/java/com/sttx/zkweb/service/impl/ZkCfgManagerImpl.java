package com.sttx.zkweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttx.zkweb.mapper.ZkConfigMapper;
import com.sttx.zkweb.model.ZkConfig;
import com.sttx.zkweb.service.ZkCfgManager;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ZkCfgManagerImpl
 * @Description: zookeeper 配置实现类
 * @author: chenchaoyun0
 * @date: 2016年8月21日 下午2:35:06
 */
@Service
@Slf4j
public class ZkCfgManagerImpl implements ZkCfgManager {

  @Autowired
  private ZkConfigMapper zkConfigMapper;

  public void add(ZkConfig zkConfig) throws Exception {
    try {
      log.info("add zkConfig:{}" + zkConfig);
      zkConfigMapper.insert(zkConfig);
    } catch (Exception e) {
      log.error("add zkCfg error : {}", e.getMessage(), e);
      throw new Exception("insert zkconfig exception");
    }
  }

  public List<ZkConfig> query() throws Exception {
    try {
      log.info("query List<ZkConfig>.");
      return zkConfigMapper.selectList(null);
    } catch (Exception e) {
      log.error("queryzkCfg error : {}", e.getMessage(), e);
      throw new Exception("select zkconfig exception");
    }
  }

  public void update(ZkConfig zkConfig) throws Exception {
    try {
      log.info("update List<ZkConfig> : {}", zkConfig);
      UpdateWrapper<ZkConfig> zkConfigUpdateWrapper = new UpdateWrapper<>();
      zkConfigUpdateWrapper.eq("zk_Id", zkConfig.getZkId());
      zkConfigMapper.update(zkConfig, zkConfigUpdateWrapper);
    } catch (Exception e) {
      log.error("update kCfg error : {}", e.getMessage(), e);
      throw new Exception("update zkconfig exception");
    }
  }

  public void delete(String zkId) throws Exception {
    try {
      log.info("delete ZkConfig by zkId : {}", zkId);
      zkConfigMapper.deleteById(zkId);
    } catch (Exception e) {
      log.error("delete id={} zkCfg error : {}", zkId, e.getMessage(), e);
      throw new Exception("delete zkconfig exception");
    }
  }

  public ZkConfig findById(String zkId) throws Exception {
    try {
      log.info("find ZkConfig by zkId : {}", zkId);
      ZkConfig zkConfig = zkConfigMapper.selectById(zkId);
      return zkConfig;
    } catch (Exception e) {
      log.error("findById id={} zkCfg error : {}", zkId, e.getMessage(), e);
      throw new Exception("SELECT zkconfig exception");
    }
  }

  public List<ZkConfig> query(int page, int rows) throws Exception {
    try {
      log.info("query ZkConfig pages : {},{}", page, rows);
      return zkConfigMapper.selectList(null);
    } catch (Exception e) {
      log.error("query ZkConfig pages error : {}", e.getMessage(), e);
      throw new Exception("SELECT page zkconfig exception");
    }
  }

  public int count() throws Exception {
    try {
      log.info("query ZkConfig count : {}");
      return zkConfigMapper.selectCount(null);
    } catch (Exception e) {
      log.error("count id={} zkCfg error : {}", e.getMessage(), e);
      throw new Exception("SELECT page zkconfig exception");
    }
  }

}
