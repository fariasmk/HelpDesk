package com.maikon.userserviceapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class openApiConfig {

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${springdoc.openapi.info.title}") final String title,
            @Value("${springdoc.openapi.info.description}") final String description,
            @Value("${springdoc.openapi.info.version}") final String version
    ) {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .description(description)
                        .version(version)
                );
    }
}