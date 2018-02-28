package com.sttx.zkweb.util;

import com.sttx.zkweb.service.ZkCfgManager;
import com.sttx.zkweb.service.impl.ZkCfgManagerImpl;

public class ZkCfgFactory {

    private static ZkCfgManager zkInstance = new ZkCfgManagerImpl();

    public static ZkCfgManager getZkCfgManager() {

        return zkInstance;
    }

}
