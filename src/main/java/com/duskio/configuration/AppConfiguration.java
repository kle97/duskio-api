package com.duskio.configuration;

import org.jdbi.v3.core.ConnectionFactory;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.spring5.SpringConnectionFactory;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
public class AppConfiguration {
    
    @Bean
    public Jdbi jdbi(DataSource dataSource) {
        ConnectionFactory connectionFactory = new SpringConnectionFactory(dataSource);
        return Jdbi.create(connectionFactory)
                   .installPlugin(new SqlObjectPlugin())
                   .registerRowMapper(new AutoRowMapperFactory());
    }
    
}
