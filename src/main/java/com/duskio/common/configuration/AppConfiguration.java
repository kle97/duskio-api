package com.duskio.common.configuration;

import com.duskio.common.repository.CustomRepositoryImpl;
import com.duskio.common.service.EnglishDictionary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.io.IOException;

@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ApplicationProperties.class)
@EnableJpaRepositories(basePackages = {"com.duskio.features"}, repositoryBaseClass = CustomRepositoryImpl.class)
@EnableJpaAuditing
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class AppConfiguration {

    // configure auditor provider for auditing classes
    @Bean
    public AuditorAware<String> auditingProvider() {
        return new CustomAuditorAware();
    }
    
    @Bean
    public EnglishDictionary englishDictionary() throws IOException {
        return new EnglishDictionary();
    }
}
