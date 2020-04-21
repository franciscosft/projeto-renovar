package com.renovar.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.renovar.domain.Usuario;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {
	
	@Transactional(readOnly=true)
	Usuario findByEmail(String email);

}
