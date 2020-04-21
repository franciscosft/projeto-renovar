package com.renovar.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renovar.RenovarApplication;
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

@Service
public class DBService {
	private final Logger log = LoggerFactory.getLogger(DBService.class);

	@Autowired
	private DispositivoDAO sensorDAO;

	@Autowired
	private IndicadorDAO indicadorDAO;

	@Autowired
	private ColetaDAO coletaDAO;

	@Autowired
	private UsuarioDAO usuarioDAO;

	public void instanciarBancoDeDados() throws InterruptedException {
		log.info("Criando objetos mock");
		Usuario usuario = new Usuario(null, "Francisco", "Teixeira", "franciscosft@gmail.com", "123");
		usuarioDAO.save(usuario);

		Coordenada coordenada = new Coordenada(-27.599645, -48.518083);
		Coordenada coordenada2 = new Coordenada(-27.6001426, -48.5182837);

		Dispositivo dispositivo = new Dispositivo(null, "Dispostivo 1", "abc", coordenada, usuario);
		Dispositivo dispositivo2 = new Dispositivo(null, "Dispostivo 2", "cdf", coordenada2, usuario);
		usuarioDAO.save(usuario);

		Indicador co = new Indicador(null, "CO", Unidade.CONCENTRACAO, 0.8);
		Indicador temperatura = new Indicador(null, "Termometro", Unidade.TEMPERATURA);
		Indicador pressao = new Indicador(null, "Pressao atmosferica", Unidade.PRESSAO);

		// Settando os indicadores que cada sensor vai ter
		dispositivo.getIndicadores().addAll(Arrays.asList(co, temperatura, pressao));
		dispositivo2.getIndicadores().addAll(Arrays.asList(temperatura, pressao));

		indicadorDAO.saveAll(Arrays.asList(co, temperatura, pressao));
		sensorDAO.saveAll(Arrays.asList(dispositivo, dispositivo2));
		Calendar instance = Calendar.getInstance();
		log.info("Adicionando coletas");
		ArrayList<Coleta> coletas = new ArrayList<>();
		for (int i = 0; i <= 20; i++) {
			coletas.add(new Coleta(null, Math.random(), getData(), coordenada, dispositivo, co));
			coletas.add(new Coleta(null, Math.random(), getData(), coordenada, dispositivo, temperatura));
			coletas.add(new Coleta(null, Math.random(), getData(), coordenada, dispositivo, pressao));
			coletas.add(new Coleta(null, Math.random(), getData(), coordenada, dispositivo2, temperatura));
			Thread.sleep(1000);
		}
		log.info("Coletas adicionadas");

		coletaDAO.saveAll(coletas);
	}
	
	public Date getData() {
		 GregorianCalendar gc = new GregorianCalendar();

	        int year = randBetween(2017, 2018);

	        gc.set(gc.YEAR, year);

	        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

	        gc.set(gc.DAY_OF_YEAR, dayOfYear);

	        System.out.println(gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.DAY_OF_MONTH));
	        
	        return gc.getTime();
	}
	
	 public static int randBetween(int start, int end) {
	        return start + (int)Math.round(Math.random() * (end - start));
	    }

}
