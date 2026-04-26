package com.renovar.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.renovar.dao.ColetaDAO;
import com.renovar.domain.Coleta;
import com.renovar.domain.Coordenada;
import com.renovar.domain.Dispositivo;
import com.renovar.domain.Indicador;
import com.renovar.dto.ColetaRequisicaoDTO;
import com.renovar.services.exceptions.ObjectNotFoundException;

@Service
public class ColetaService {
	private final Logger log = LoggerFactory.getLogger(ColetaService.class);

	private final ColetaDAO dao;

	private final DispositivoService dispositivoService;

	private final IndicadorService indicadorService;

	public ColetaService(ColetaDAO dao, DispositivoService dispositivoService, IndicadorService indicadorService) {
		this.dao = dao;
		this.dispositivoService = dispositivoService;
		this.indicadorService = indicadorService;
	}

	public Coleta buscarColeta(Integer idColeta) {
		Optional<Coleta> findById = dao.findById(idColeta);
		return findById.orElseThrow(() -> new ObjectNotFoundException(
				"Coleta não encontrada: " + idColeta + " , Tipo: " + Coleta.class.getName()));
	}

	public List<Coleta> buscarColetasDispositivo(Integer idDispositivo) {
        return dao.buscarColetasDispositivo(idDispositivo);
	}
	
	public List<Coleta> buscarColetasDispositivoIndicador(Integer idDispositivo, Integer idIndicador) {
		return dao.buscarColetasDispositivoIndicador(idDispositivo, idIndicador);
	}
	
	public List<Coleta> buscarColetasDispositivoIndicadorData(Integer idDispositivo, Integer idIndicador,
			Date dataInicio, Date dataFim) {
		log.info("Data: {} e fim: {}", dataInicio, dataFim);
		return dao.buscarColetasDispositivoIndicadorData(idDispositivo, idIndicador, dataInicio, dataFim);
	}

	public Page<Coleta> buscarDispositivoPorPagina(Integer idDispositivo, Integer pagina, Integer linhasPagina, String ordenacao, String direcao) {
		PageRequest pageRequest = PageRequest.of(pagina, linhasPagina, Direction.valueOf(direcao), ordenacao);
		return dao.buscarColetasDispositivoPorPagina(idDispositivo, pageRequest);
	}
	
	public Coleta buscarUltimaColetaDispositivo(Integer idDispositivo) {
		List<Coleta> buscarColetasDispositivo = buscarColetasDispositivo(idDispositivo);
		return buscarColetasDispositivo.get(0);
	}

	public Page<Coleta> encontrarColetasDeDispositivo(Integer pagina, Integer linhasPagina, String ordenacao, String direcao) {
		PageRequest pageRequest = PageRequest.of(pagina, linhasPagina, Direction.valueOf(direcao), ordenacao);
		return dao.findAll(pageRequest);
	}
	
	public List<Coleta> buscarTodas() {
		return dao.findAll();
	}

	@Transactional
	public Coleta inserir(Coleta coleta) {
		return dao.save(coleta);
	}


	public Coleta toColeta(ColetaRequisicaoDTO coletaDTO) {
		Date data = new Date();
		double medida = coletaDTO.medida();
		Integer dispositivoId = coletaDTO.dispositivoId();
		Integer indicadorId = coletaDTO.indicadorId();
		Dispositivo dispositivo = dispositivoService.buscar(dispositivoId);
		Indicador indicador = indicadorService.buscar(indicadorId);
		Coordenada coordenada = new Coordenada(dispositivo.getLatitude(), dispositivo.getLongitude());
		Coleta coleta = new Coleta(null, medida, data, coordenada, dispositivo, indicador);
		log.info("Coleta para ser adicionada: {}", coleta);
		return coleta;
	}

	

}
