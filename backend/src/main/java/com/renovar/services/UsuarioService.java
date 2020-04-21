package com.renovar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.renovar.dao.UsuarioDAO;
import com.renovar.domain.Usuario;
import com.renovar.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDAO dao;

	public Usuario buscar(Integer id) {
		Optional<Usuario> findById = dao.findById(id);
		return findById.orElseThrow(() -> new ObjectNotFoundException(
				"Usuario não encontrado: " + id + " , Tipo: " + Usuario.class.getName()));
	}

	public List<Usuario> buscarTodos() {
		return dao.findAll();
	}

	public Usuario inserir(Usuario usuario) {
		usuario.setId(null);
		return dao.save(usuario);
	}

	public Usuario atualizar(Usuario usuario) {
		// TODO: definir quais são as informações aptas para atualização
		Usuario usuarioEncontrado = buscar(usuario.getId());
		usuarioEncontrado.setNome(usuario.getNome());
		usuarioEncontrado.setEmail(usuario.getEmail());
		return dao.save(usuarioEncontrado);
	}

	public Page<Usuario> encontrarPagina(Integer pagina, Integer linhasPagina, String ordenacao, String direcao) {
		PageRequest pageRequest = PageRequest.of(pagina, linhasPagina, Direction.valueOf(direcao), ordenacao);
		return dao.findAll(pageRequest);
	}

}
