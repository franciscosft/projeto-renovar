package com.renovar;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.renovar.dao.UsuarioDAO;
import com.renovar.domain.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RenovarApplicationTests {

	@Autowired
	UsuarioDAO dao;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
    public void diagnosticarAtualizacao(){
		Optional<Usuario> findById = dao.findById(1);
		Usuario usuario2 = findById.get();
//		Usuario usuario2 = new Usuario();
//		usuario2.setId(1);
		usuario2.setNome("OutroNome");
		
		List<Usuario> findAll = dao.findAll();
		for (Usuario usuario : findAll) {
			System.out.println(usuario);
		}
		
		dao.save(usuario2);
		
		System.out.println("-----------------------------------------------");
		
		findAll = dao.findAll();
		for (Usuario usuario : findAll) {
			System.out.println(usuario);
		}
	}
	
	@Test
	public void testEmail() {
		Usuario findByEmail = dao.findByEmail("franciscosft@gmail.com");
		Assert.assertNotNull(findByEmail);
	}

}
