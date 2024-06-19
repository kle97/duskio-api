package com.duskio.configuration;

import com.duskio.DuskioApiApplication;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.ConnectionFactory;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import org.jdbi.v3.spring5.EnableJdbiRepositories;
import org.jdbi.v3.spring5.SpringConnectionFactory;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@EnableJdbiRepositories(basePackageClasses = DuskioApiApplication.class)
@EnableConfigurationProperties(ApplicationProperties.class)
@Configuration(proxyBeanMethods = false)
public class AppConfiguration {
    
    @Bean
    public Jdbi jdbi(DataSource dataSource) {
        ConnectionFactory connectionFactory = new SpringConnectionFactory(dataSource);
        return Jdbi.create(connectionFactory)
                   .setSqlLogger(new Slf4JSqlLogger(log))
                   .installPlugin(new SqlObjectPlugin())
                   .registerRowMapper(new AutoRowMapperFactory());
    }
    
}
