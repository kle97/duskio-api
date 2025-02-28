package com.duskio.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@Slf4j
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@EnableJdbcRepositories(basePackages = {"com.duskio.features"})
@EnableJdbcAuditing
@EnableConfigurationProperties(ApplicationProperties.class)
@Configuration(proxyBeanMethods = false)
public class AppConfiguration {
    
}
