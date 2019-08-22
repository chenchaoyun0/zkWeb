package com.sttx.zkweb;

import com.alibaba.fastjson.JSON;
import com.sttx.zkweb.config.BootConfig;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.sttx.zkweb.model.ZkConfig;
import com.sttx.zkweb.service.ZkCfgManager;
import com.sttx.zkweb.service.impl.ZkManagerImpl;
import com.sttx.zkweb.util.SpringUtils;
import com.sttx.zkweb.util.ZkCache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.sttx.zkweb.mapper")
@Slf4j
public class ZkWebApplication extends SpringBootServletInitializer
    implements ApplicationListener<ContextRefreshedEvent> {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(ZkWebApplication.class);
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(ZkWebApplication.class, args);
    log.info(">>>>>zookeeper管理系统启动成功!");
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    log.info("开机服务执行的操作....");
    try {
      ZkCfgManager zkCfgManager = SpringUtils.getBean(ZkCfgManager.class);
      for (ZkConfig zkConfig : zkCfgManager.query()) {
        log.info("ID : {},CONNECTSTR : {},SESSIONTIMEOUT : {}",
            new Object[]{zkConfig.getZkId(), zkConfig.getZkConnectStr(), zkConfig.getZkSessionTimeOut()});
        ZkManagerImpl zk = new ZkManagerImpl(zkConfig.getZkConnectStr(),
            Integer.parseInt(zkConfig.getZkSessionTimeOut()), zkConfig.getZkUserName(), zkConfig.getZkUserPwd());
        ZkCache.put(zkConfig.getZkId(), zk);
      }
    } catch (NumberFormatException e) {
      log.error("数字格式化异常 :error{}", e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception error:{}", e.getMessage(), e);
    }

    for (String key : ZkCache.getZkCache().keySet()) {
      log.info("key : {} , zk : {}", key, ZkCache.get(key));
    }

    try {
      BootConfig bootConfig = SpringUtils.getBean(BootConfig.class);
      log.info("输入配置:{}", JSON.toJSONString(bootConfig));
      Environment env = SpringUtils.getBean(Environment.class);
      String protocol = "http";
      if (env.getProperty("server.ssl.key-store") != null) {
        protocol = "https";
      }
      log.info(
          "\n---------------------------------------------------------------------------------------\n\t"
              +
              "Application '{}' is running! Access URLs:\n\t" +
              "Local: \t\t{}://{}:{}/zkWeb\n\t" +
              "External: \t{}://{}:{}/zkWeb/h2\n\t" +
              "\n---------------------------------------------------------------------------------------",
          env.getProperty("spring.application.name"),
          protocol,
          InetAddress.getLocalHost().getHostAddress(),
          env.getProperty("server.port"),
          protocol,
          InetAddress.getLocalHost().getHostAddress(),
          env.getProperty("server.port"));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

}