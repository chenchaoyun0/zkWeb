package com.sttx.zkweb.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil {
    private static Properties props = new Properties();
    private static final Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

    public static String getFilePath(String propertiesFilePath, String propertiesKey) throws ZkException {
        try {
            log.info("getFilePath propertiesFilePath:{},propertiesKey:{}", propertiesFilePath, propertiesKey);
            // 获取配置文件内容
            props.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesFilePath));
        } catch (IOException e) {
            log.error("getFilePath error:{}", e.getMessage(), e);
            throw new ZkException("读取配置文件出错");
        }
        return props.getProperty(propertiesKey);
    }
}
