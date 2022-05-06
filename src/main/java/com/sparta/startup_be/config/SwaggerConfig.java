package com.sparta.startup_be.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {
        "com.sparta.startup_be.controller"
})
public class SwaggerConfig {

    @Bean
    public Docket Api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sparta.startup_be.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.ApiInfo());
    }

    private ApiInfo ApiInfo() {
        return new ApiInfoBuilder()
                .title("Ossack API")
                .description("API설명")
                .version("1.0")
                .build();
    }
}