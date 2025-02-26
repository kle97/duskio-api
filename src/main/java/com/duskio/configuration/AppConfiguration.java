package com.duskio.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Slf4j
@EnableJdbcRepositories
@EnableJdbcAuditing
@EnableConfigurationProperties(ApplicationProperties.class)
@Configuration(proxyBeanMethods = false)
public class AppConfiguration {
    
}
