package com.duskio.configuration;

import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.ConnectionFactory;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import org.jdbi.v3.spring5.SpringConnectionFactory;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
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
