package com.sasip.config;

import java.util.Collections;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

@Configuration
@EnableSwagger2
@ConditionalOnClass(Swagger2DocumentationConfiguration.class)
@Import(Swagger2DocumentationConfiguration.class)
public class SwaggerConfig
{

    @Bean
    public Docket api()
    {

        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("org.ff4j.spring.boot.web.api.resources")).paths(PathSelectors.any()).build()
                .apiInfo(apiInfo()).useDefaultResponseMessages(false);

    }

    private ApiInfo apiInfo()
    {
        return new ApiInfo("FF4J Demo Feature Store (with Spring Boot Starter and Web)", "Demo varius FF4J capabilities", "1.8.1", "Terms of service",
                new Contact("Sasi Peri", "http://localhost", "me@myself.com"), "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0.html", Collections.emptyList());
    }

}
