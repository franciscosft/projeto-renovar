package com.renovar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.renovar.config.security.ApiKeyFilter;
import com.renovar.config.security.SubscriptionKeyFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${security.device.api-key}")
    private String deviceApiKey;

    @Value("${security.frontend.subscription-key}")
    private String frontendSubscriptionKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Swagger UI and OpenAPI spec — public
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/webjars/**"
                        ).permitAll()
                        // IoT device endpoint — requires X-Api-Key
                        .requestMatchers(HttpMethod.POST, "/reading/ingest").hasRole("DEVICE")
                        // Frontend read endpoints — requires X-Subscription-Key
                        .requestMatchers(HttpMethod.GET, "/**").hasRole("FRONTEND")
                        // Everything else is denied
                        .anyRequest().denyAll()
                )
                .addFilterBefore(new ApiKeyFilter(deviceApiKey),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new SubscriptionKeyFilter(frontendSubscriptionKey),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}