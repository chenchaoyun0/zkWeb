package com.sttx.zkweb.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sttx.zkweb.model.ZkConfig;
import com.sttx.zkweb.service.ZkCfgManager;
import com.sttx.zkweb.service.impl.ZkManagerImpl;

/**
 * 
 * @ClassName: ZkCache
 * @Description: zookeeper缓存类，是一个线程中的MAP，key为ZkConfig配置的zkId
 * @author: chenchaoyun0
 * @date: 2016年8月22日 下午2:00:04
 */
public class ZkCache {

    private static Map<String, ZkManagerImpl> zkCache = new ConcurrentHashMap<String, ZkManagerImpl>();
    private static final Logger log = LoggerFactory.getLogger(ZkCache.class);

    public static ZkManagerImpl put(String key, ZkManagerImpl zk) {
        return zkCache.put(key, zk);
    }

    public static ZkManagerImpl get(String key) {
        return zkCache.get(key);
    }

    public static ZkManagerImpl remove(String key) {
        return zkCache.remove(key);
    }

    public static int size() {
        return zkCache.size();
    }

    public static Map<String, ZkManagerImpl> getZkCache() {
        return zkCache;
    }

    public static void setZkCache(Map<String, ZkManagerImpl> zkCache) {
        ZkCache.zkCache = zkCache;
    }

    public static void init(ZkCfgManager cfgManager) {
        List<ZkConfig> list = null;
        try {
            list = cfgManager.query();
            for (ZkConfig zkConfig : list) {
                ZkManagerImpl zk = new ZkManagerImpl(zkConfig.getZkConnectStr(),
                        Integer.parseInt(zkConfig.getZkSessionTimeOut()), zkConfig.getZkUserName(),
                        zkConfig.getZkUserPwd());
                ZkCache.put(zkConfig.getZkId(), zk);
            }
            // Thread checkingThread = new Thread(new CheckingTask());
            // checkingThread.start();
        } catch (ZkException e) {
            log.error("init -", e.getMessage(), e);
        }
    }

    static class CheckingTask implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Entry<String, ZkManagerImpl> entry : zkCache.entrySet()) {
                    ZkManagerImpl zk = entry.getValue();
                    if (!zk.checkConnection()) {
                        zkCache.remove(entry.getKey());
                    }
                }
            }
        }
    }
}