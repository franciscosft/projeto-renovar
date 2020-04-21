package com.renovar.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.renovar.dao.DispositivoDAO;
import com.renovar.domain.Coleta;
import com.renovar.domain.Dispositivo;
import com.renovar.dto.DispositivoDTO;
import com.renovar.services.exceptions.DataIntegrityException;
import com.renovar.services.exceptions.ObjectNotFoundException;

@Service
public class DispositivoService {
	private final Logger log = LoggerFactory.getLogger(DispositivoService.class);

	@Autowired
	private DispositivoDAO dao;

	@Autowired
	private UsuarioService usuarioService;

	public Dispositivo buscar(Integer id) {
		Optional<Dispositivo> findById = dao.findById(id);
		return findById.orElseThrow(() -> new ObjectNotFoundException(
				"Sensor não encontrado: " + id + " , Tipo: " + Dispositivo.class.getName()));
	}

	public List<Dispositivo> buscarTodos() {
		return dao.findAll();
	}

	public Dispositivo inserir(Dispositivo sensor) {
		return dao.save(sensor);
	}

	public Dispositivo atualizar(Dispositivo sensor) {
		// TODO: definir quais são as informações aptas para atualização
		Dispositivo buscar = buscar(sensor.getId());
		buscar.setNome(sensor.getNome());
		return dao.save(buscar);
	}

	public void deletar(Integer id) {
		buscar(id);
		try {
			dao.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um sensor que possua entidade dependentes", e);
		}
	}

	public Dispositivo toDispositivo(DispositivoDTO sensorDTO) {
		log.debug("SensorDTO: {}", sensorDTO);
		return new Dispositivo(sensorDTO.getId(), sensorDTO.getNome(), sensorDTO.getCodigoRastreio(),
				sensorDTO.getCoordenada(), usuarioService.buscar(sensorDTO.getUsuarioId()));
	}
	
	public Page<Dispositivo> encontrarPagina(Integer pagina, Integer linhasPagina, String ordenacao, String direcao) {
		PageRequest pageRequest = PageRequest.of(pagina, linhasPagina, Direction.valueOf(direcao), ordenacao);
		return dao.findAll(pageRequest);
	}
	
	public DispositivoDTO toDispositivoDTO(Dispositivo dispositivo) {
		DispositivoDTO dispositivoDTO = new DispositivoDTO(dispositivo);
		log.info("Indicadores: {}", dispositivo.getIndicadores());
		dispositivoDTO.setIndicadores(dispositivo.getIndicadores());
		return dispositivoDTO;
	}

}
