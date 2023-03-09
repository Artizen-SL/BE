package com.example.artizen.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    //Docket class는 Swagger 설정을 도와주는 클래스.
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext())) // JWT
                .securitySchemes(Arrays.asList(apiKey())); // JWT
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Artizen")
                .description("위치기반 문화컨텐츠 추천 어플리케이션")
                .version("version 1")
                .contact(new Contact("아티즌", "홈페이지 URL", "e-mail"))
                .build();
    }

    //Api key 만들어주기
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    //JWT SecurityContext
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    //전역으로 JWT 적용.
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEveryThing");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}

