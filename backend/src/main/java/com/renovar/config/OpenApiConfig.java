package com.renovar.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public static final String SCHEME_SUBSCRIPTION = "X-Subscription-Key";
    public static final String SCHEME_API_KEY      = "X-Api-Key";
    public static final String SCHEME_BEARER       = "Bearer";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Renovar API")
                        .description("REST API for environmental quality monitoring via IoT devices")
                        .version("0.0.2"))
                .components(new Components()
                        .addSecuritySchemes(SCHEME_SUBSCRIPTION,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name(SCHEME_SUBSCRIPTION)
                                        .description("Required for all GET endpoints. Pass the value configured in FRONTEND_SUBSCRIPTION_KEY."))
                        .addSecuritySchemes(SCHEME_API_KEY,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name(SCHEME_API_KEY)
                                        .description("Required for POST /reading/ingest. Pass the value configured in DEVICE_API_KEY."))
                        .addSecuritySchemes(SCHEME_BEARER,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT obtained from POST /auth/login or /auth/register. Required for POST /devices and POST /indicators.")));
    }

}