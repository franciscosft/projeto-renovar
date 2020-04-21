package com.renovar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.renovar.dao.ColetaDAO;
import com.renovar.dao.DispositivoDAO;
import com.renovar.dao.IndicadorDAO;
import com.renovar.dao.UsuarioDAO;
import com.renovar.domain.Coleta;
import com.renovar.domain.Coordenada;
import com.renovar.domain.Dispositivo;
import com.renovar.domain.Indicador;
import com.renovar.domain.Usuario;
import com.renovar.domain.enums.Unidade;

@SpringBootApplication
public class RenovarApplication implements CommandLineRunner {
	private final Logger log = LoggerFactory.getLogger(RenovarApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RenovarApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}

}
