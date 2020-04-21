package com.renovar.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.renovar.domain.Coleta;
import com.renovar.domain.Dispositivo;
import com.renovar.domain.Indicador;
import com.renovar.dto.ColetaRespostaDTO;
import com.renovar.dto.DispositivoDTO;
import com.renovar.services.DispositivoService;
import com.renovar.services.IndicadorService;

@CrossOrigin
@RestController
@RequestMapping(value = "/dispositivo")
public class DispositivoResource {
	private static Logger log = LoggerFactory.getLogger(DispositivoResource.class);

	@Autowired
	private DispositivoService service;

	/*
	 * Método utilizado para buscar um determinado dispositivo
	 * @param: id
	 * 
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<DispositivoDTO> buscarDispositivo(@PathVariable Integer id) {
		Dispositivo dispositivo = service.buscar(id);
		DispositivoDTO dispositivoDTO = service.toDispositivoDTO(dispositivo);
		return ResponseEntity.ok().body(dispositivoDTO);
	}

	@RequestMapping(value = "/todos", method = RequestMethod.GET)
	public ResponseEntity<List<DispositivoDTO>> buscarDispositivos() {
		log.info("Buscando todos os dispositivos");
		List<Dispositivo> dispositivos = service.buscarTodos();
		List<DispositivoDTO> dispositivosDTO = dispositivos.stream().map(s -> service.toDispositivoDTO(s)).collect(Collectors.toList());
		ResponseEntity<List<DispositivoDTO>> response = ResponseEntity.ok().body(dispositivosDTO);
		return response;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> cadastrarDispositvo(@Valid @RequestBody DispositivoDTO dispositivoDTO) {
		log.info("DispositivoDTO: {}", dispositivoDTO);
		Dispositivo dispositivo = service.toDispositivo(dispositivoDTO);
//		List<Integer> indicadoresId = dispositivoDTO.getIndicadoresId();
//		List<Indicador> indicadores = indicadorService.toIndicadores(indicadoresId);
//		dispositivo.setIndicadores(indicadores);
		dispositivo = service.inserir(dispositivo);
		// Boa prática de desenvolvendo para retornar a URI do novo objeto criado
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dispositivo.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizarDispositivo(@RequestBody Dispositivo dispositivo, @PathVariable Integer id) {
		// Definir quais atributos serão utilizados na atualização
		log.info("Dispositivo: {}", dispositivo);
		dispositivo.setId(id);
		dispositivo = service.atualizar(dispositivo);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/pagina", method = RequestMethod.GET)
	public ResponseEntity<Page<DispositivoDTO>> buscarPagina(
			@RequestParam(value = "page", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linesPerPage", defaultValue = "10") Integer linhasPagina,
			@RequestParam(value = "orderBy", defaultValue = "id") String ordenacao,
			@RequestParam(value = "direction", defaultValue = "ASC") String direcao) {
		Page<Dispositivo> dispositivo = service.encontrarPagina(pagina, linhasPagina, ordenacao, direcao);
		Page<DispositivoDTO> dtos = dispositivo.map(d -> new DispositivoDTO(d));
		return ResponseEntity.ok().body(dtos);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletarDispositivo(@PathVariable Integer id) {
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}

}
