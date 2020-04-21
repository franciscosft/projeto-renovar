package com.renovar.resources;

import java.net.URI;
import java.util.List;

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

import com.renovar.domain.Indicador;
import com.renovar.domain.Usuario;
import com.renovar.dto.UsuarioDTO;
import com.renovar.services.IndicadorService;
import com.renovar.services.UsuarioService;

@RestController
@RequestMapping(value = "/indicadores")
public class IndicadorResource {

	@Autowired
	private IndicadorService service;

	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarIndicador(@PathVariable Integer id) {
		Indicador indicador = service.buscar(id);
		return ResponseEntity.ok().body(indicador);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> buscarIndicadores() {
		List<Indicador> indicador = service.buscarTodos();
		return ResponseEntity.ok().body(indicador);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> cadastrarIndicador(@RequestBody Indicador indicador) {
		indicador = service.inserir(indicador);
		// Boa prática de desenvolvendo para retornar a URI do novo objeto criado
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(indicador.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> atualizarIndicador(@RequestBody Indicador indicador, @PathVariable Integer id) {
		indicador.setId(id);
		indicador = service.atualizar(indicador);
		return ResponseEntity.noContent().build();
	}

}
