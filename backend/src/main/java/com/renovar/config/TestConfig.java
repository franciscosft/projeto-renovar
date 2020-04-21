package com.renovar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.renovar.services.DBService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	DBService service;
	
	@Bean
	public boolean  instanciarBandoDeDados() throws InterruptedException {
		service.instanciarBancoDeDados();
		return true;
	}
}
