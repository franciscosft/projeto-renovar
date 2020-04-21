package com.renovar.resources;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.renovar.RenovarApplication;
import com.renovar.domain.Coleta;
import com.renovar.dto.ColetaRequisicaoDTO;
import com.renovar.dto.ColetaRespostaDTO;
import com.renovar.dto.DispositivoDTO;
import com.renovar.services.ColetaService;
import com.renovar.util.RenovarUtils;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/coletas")
public class ColetaResource {
	private final Logger log = LoggerFactory.getLogger(ColetaResource.class);

	@Autowired
	private ColetaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Coleta> buscarColeta(@PathVariable Integer id) {
		Coleta coleta = service.buscarColeta(id);
		return ResponseEntity.ok().body(coleta);
	}

	@CrossOrigin
	@RequestMapping(value = "/dispositivo/{idDispositivo}", method = RequestMethod.GET)
	public ResponseEntity<List<ColetaRespostaDTO>> buscarColetasDispositivo(@PathVariable Integer idDispositivo) {
		List<Coleta> coletas = service.buscarColetasDispositivo(idDispositivo);
		List<ColetaRespostaDTO> collect = coletas.stream().map(c -> new ColetaRespostaDTO(c))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(collect);
	}

	@CrossOrigin
	@RequestMapping(value = "/{idDispositivo}/{idIndicador}", method = RequestMethod.GET)
	public ResponseEntity<List<ColetaRespostaDTO>> buscarColetasDispositivoIndicador(
			@PathVariable Integer idDispositivo, 
			@PathVariable Integer idIndicador){
		List<Coleta> coletas = service.buscarColetasDispositivoIndicador(idDispositivo, idIndicador);
		List<ColetaRespostaDTO> collect = coletas.stream().map(c -> new ColetaRespostaDTO(c))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(collect);
	}

	@CrossOrigin
	@RequestMapping(value = "/intervalo/", method = RequestMethod.GET)
	public ResponseEntity<List<ColetaRespostaDTO>> buscarIndicadoresDatas(
			@RequestParam(value = "idDispositivo", defaultValue = "") Integer idDispositivo, 
			@RequestParam(value = "idIndicador", defaultValue = "") Integer idIndicador,
			@RequestParam(value = "dataInicio", defaultValue = "") String inicio,
			@RequestParam(value = "dataFim", defaultValue = "") String fim) {
		
		if(inicio.isEmpty() || fim.isEmpty()) {
			return ResponseEntity.badRequest().body(null);
		}
		
		Date dataInicio2 = RenovarUtils.toDataInicio(inicio);
		Date dataFim2 = RenovarUtils.toDataFim(fim);
		log.info("Buscando coletas entre {} e {}", dataInicio2, dataFim2);
		
		List<Coleta> coletas = service.buscarColetasDispositivoIndicadorData(idDispositivo, idIndicador, dataInicio2, dataFim2);
		List<ColetaRespostaDTO> collect = coletas.stream().map(c -> new ColetaRespostaDTO(c))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(collect);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ColetaRespostaDTO>> buscarPagina(
			@RequestParam(value = "idDispositivo", defaultValue = "") Integer idDispositivo,
			@RequestParam(value = "page", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linesPerPage", defaultValue = "5") Integer linhasPagina,
			@RequestParam(value = "orderBy", defaultValue = "data") String ordenacao,
			@RequestParam(value = "direction", defaultValue = "DESC") String direcao) {
		Page<Coleta> coleta = service.buscarDispositivoPorPagina(idDispositivo, pagina, linhasPagina, ordenacao,
				direcao);
		Page<ColetaRespostaDTO> dtos = coleta.map(c -> new ColetaRespostaDTO(c));
		return ResponseEntity.ok().body(dtos);
	}

	@RequestMapping(value = "/todas", method = RequestMethod.GET)
	public ResponseEntity<List<ColetaRespostaDTO>> buscarTodas() {
		List<Coleta> coleta = service.buscarTodas();
		List<ColetaRespostaDTO> dtos = coleta.stream().map(c -> new ColetaRespostaDTO(c)).collect(Collectors.toList());
		return ResponseEntity.ok().body(dtos);
	}

	/**
	 * Método responsável por buscar a última Coleta de um dispositivo
	 * 
	 * @param idDispositivo
	 * @return Coleta
	 */
	@RequestMapping(value = "/dispositivo/ultima/{idDispositivo}", method = RequestMethod.GET)
	public ResponseEntity<Coleta> buscarUltimaColetaDispositivo(@PathVariable Integer idDispositivo) {
		Coleta coleta = service.buscarUltimaColetaDispositivo(idDispositivo);
		return ResponseEntity.ok().body(coleta);
	}

	/**
	 * Método utilizado para inserir uma coleta
	 * 
	 * @param coletaDTO
	 * @return
	 */
	@ApiOperation(value = "Método utilizado para adicionar uma coleta")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> cadastrarColeta(@RequestBody ColetaRequisicaoDTO coletaDTO) {
		Coleta coleta = service.toColeta(coletaDTO);
		coleta = service.inserir(coleta);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(coleta.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/teste", method = RequestMethod.POST)
	public ResponseEntity<Void> test(@RequestBody ColetaRequisicaoDTO teste) {
		log.info("Parametro passado no teste: {}", teste);
		return ResponseEntity.noContent().build();
	}

}
