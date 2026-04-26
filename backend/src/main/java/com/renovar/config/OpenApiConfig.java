package com.renovar.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Projeto Renovar API")
                        .description("API de monitoramento de qualidade ambiental via dispositivos IoT")
                        .version("0.0.2"));
    }

}