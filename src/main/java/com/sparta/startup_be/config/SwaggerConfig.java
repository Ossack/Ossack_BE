package com.sparta.startup_be.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

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
                .apiInfo(this.apiInfo());
//                .securitySchemes(Arrays.asList(apiKey()))
//                .securityContexts(Arrays.asList(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Ossack API")
                .description("API 상세소개 및 사용법 설명하는곳")
                .version("1.0")
                .build();
    }

//    private static ApiKey apiKey() {
//        return new ApiKey("Authorization", "Bearer Token", "header");
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder().securityReferences(defaultAuth())
//                .operationSelector(oc -> oc.requestMappingPattern().startsWith("/api/")).build();
//    }
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "global access");
//        return Arrays.asList(new SecurityReference("Authorization", new AuthorizationScope[] {authorizationScope}));
//    }
}