package com.sparta.startup_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class StartUpBeApplication {
//
//    public static final String APPLICATION_LOCATIONS = "spring.config.location=";

    public static void main(String[] args) {
//        new SpringApplicationBuilder(StartUpBeApplication.class)
//                .properties(APPLICATION_LOCATIONS)
//                .run(args);
        SpringApplication.run(StartUpBeApplication.class, args);
    }

}
