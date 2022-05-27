package com.sparta.startup_be.login.security.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://ossack.shop") // 여기다 허용할 주소를 작성
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders(HttpHeaders.AUTHORIZATION)
//                .allowCredentials(true)
                .allowCredentials(false)
                .maxAge(this.MAX_AGE_SECS);
    }
}