package com.renovar.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.renovar.config.security.ApiKeyFilter;
import com.renovar.config.security.JwtFilter;
import com.renovar.config.security.SubscriptionKeyFilter;
import com.renovar.services.JwtService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String deviceApiKey;
    private final String frontendSubscriptionKey;
    private final JwtService jwtService;

    public SecurityConfig(
            @Value("${security.device.api-key}") String deviceApiKey,
            @Value("${security.frontend.subscription-key}") String frontendSubscriptionKey,
            JwtService jwtService) {
        this.deviceApiKey = deviceApiKey;
        this.frontendSubscriptionKey = frontendSubscriptionKey;
        this.jwtService = jwtService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
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
                        // Preflight OPTIONS requests must pass without authentication
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Auth endpoints — public
                        .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login").permitAll()
                        // IoT device endpoint — requires X-Api-Key
                        .requestMatchers(HttpMethod.POST, "/reading/ingest").hasRole("DEVICE")
                        // Frontend read endpoints — requires X-Subscription-Key
                        .requestMatchers(HttpMethod.GET, "/**").hasRole("FRONTEND")
                        // Admin write endpoints — requires JWT
                        .requestMatchers(HttpMethod.POST, "/devices").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/indicators").hasRole("USER")
                        // Everything else is denied
                        .anyRequest().denyAll()
                )
                .addFilterBefore(new JwtFilter(jwtService),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ApiKeyFilter(deviceApiKey),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new SubscriptionKeyFilter(frontendSubscriptionKey),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("X-Subscription-Key", "X-Api-Key", "Content-Type", "Authorization"));
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}