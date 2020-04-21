package com.renovar.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.renovar.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {
	private final Logger log = LoggerFactory.getLogger(DevConfig.class);


	@Autowired
	private DBService service;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean  instanciarBandoDeDados() throws InterruptedException {
		// Definindo a estratégia para a criação do banco,  se a chave spring.jpa.hibernate.ddl-auto != create  
		log.info("Estratégia definida: {}", strategy);
		if(!"create".equals(strategy)) {
			return false;
		}
		service.instanciarBancoDeDados();
		return true;
	}
}
