package com.duskio;

import org.jdbi.v3.spring5.EnableJdbiRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJdbiRepositories
public class DuskioApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DuskioApiApplication.class, args);
    }

}
