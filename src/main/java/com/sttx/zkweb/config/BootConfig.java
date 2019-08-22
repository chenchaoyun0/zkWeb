package com.sttx.zkweb.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class BootConfig {

  @Value("${server.port}")
  private String serverPort;


  @Value("${logging.appender.sentry.enabled}")
  private String sentryEnabled;

  @Value("${spring.datasource.url}")
  private String springDatasourceUrl;

  @Value("${spring.datasource.username}")
  private String springDatasourceUsername;
}
