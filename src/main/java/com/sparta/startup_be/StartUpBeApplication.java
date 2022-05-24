package com.sparta.startup_be;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class StartUpBeApplication {

    // S3
    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.properties";

    // S3
    // application.properties와 aws.yml 두개의 파일 모두를 설정 파일로 읽어서 사용
    public static void main(String[] args) {
        new SpringApplicationBuilder(StartUpBeApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }
}
