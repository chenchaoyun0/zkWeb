package com.sttx.zkweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


@SpringBootApplication
@ServletComponentScan
public class CashLoanManagerApplication extends SpringBootServletInitializer
        implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(CashLoanManagerApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CashLoanManagerApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CashLoanManagerApplication.class, args);
        log.info(">>>>>zookeeper管理系统启动成功!");
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("开机服务执行的操作....");
    }

}