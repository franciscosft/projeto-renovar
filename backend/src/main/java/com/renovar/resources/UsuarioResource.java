package com.renovar.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.renovar.domain.Usuario;
import com.renovar.dto.UsuarioDTO;
import com.renovar.services.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarUsuario(@PathVariable Integer id) {
		Usuario usuario = service.buscar(id);
		return ResponseEntity.ok().body(usuario);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> buscarUsuarios() {
		List<Usuario> usuarios = service.buscarTodos();
		return ResponseEntity.ok().body(usuarios);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> cadastrarUsuario(@RequestBody Usuario usuario) {
		usuario = service.inserir(usuario);
		// Boa prática de desenvolvendo para retornar a URI do novo objeto criado
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	// TODO pesquisar como filtrar por data superior e inferior
	@RequestMapping(value = "/pagina", method = RequestMethod.GET)
	public ResponseEntity<Page<UsuarioDTO>> buscarPagina(
			@RequestParam(value = "page", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linhasPagina,
			@RequestParam(value = "orderBy", defaultValue = "nome") String ordenacao,
			@RequestParam(value = "direction", defaultValue = "ASC") String direcao) {
		Page<Usuario> usuarios = service.encontrarPagina(pagina, linhasPagina, ordenacao, direcao);
		Page<UsuarioDTO> dtos = usuarios.map(u -> new UsuarioDTO(u));
		return ResponseEntity.ok().body(dtos);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario usuario, @PathVariable Integer id) {
		usuario.setId(id);
		usuario = service.atualizar(usuario);
		return ResponseEntity.noContent().build();
	}

}
