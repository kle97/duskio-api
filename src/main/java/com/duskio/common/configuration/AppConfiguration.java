package com.duskio.common.configuration;

import co.elastic.clients.transport.TransportUtils;
import com.duskio.common.constant.Constant;
import com.duskio.common.repository.CustomRepositoryImpl;
import com.duskio.common.service.EnglishDictionary;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.io.File;
import java.io.IOException;

@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ApplicationProperties.class)
@EnableJpaRepositories(basePackages = {"com.duskio.features"}, repositoryBaseClass = CustomRepositoryImpl.class)
@EnableJpaAuditing
@EnableElasticsearchRepositories(basePackages = {"com.duskio.indexes"})
@EnableElasticsearchAuditing
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class AppConfiguration extends ElasticsearchConfiguration {

    // configure auditor provider for auditing classes
    @Bean
    public AuditorAware<String> auditingProvider() {
        return new CustomAuditorAware();
    }
    
    @Bean
    public EnglishDictionary englishDictionary() throws IOException {
        return new EnglishDictionary();
    }

    @Nonnull
    @Override
    public ClientConfiguration clientConfiguration() {
        String fileName = System.getenv("ES_HOME") + Constant.SEPARATOR + "config" + Constant.SEPARATOR + "certs" + Constant.SEPARATOR + "http_ca.crt";
        File certFile = new File(fileName);
        try {
            return ClientConfiguration.builder()
                                      .connectedTo("localhost:9200")
                                      .usingSsl(TransportUtils.sslContextFromHttpCaCrt(certFile))
                                      .withBasicAuth("elastic", "123456")
                                      .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
