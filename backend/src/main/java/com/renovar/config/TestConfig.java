package com.renovar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.renovar.services.DBService;

@Configuration
@Profile("test")
public class TestConfig {

    private DBService service;

    public TestConfig(DBService service) {
        this.service = service;
    }

    @Bean
    public boolean initializeDatabase() throws InterruptedException {
        service.initializeDatabase();
        return true;
    }

}