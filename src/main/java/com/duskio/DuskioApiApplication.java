package com.duskio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;

@SpringBootApplication(exclude = {ElasticsearchRestClientAutoConfiguration.class})
public class DuskioApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DuskioApiApplication.class, args);
    }

}
