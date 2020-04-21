
package com.renovar.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.renovar.domain.Usuario;

public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String nome;
	private String sobreNome;

	@Email(message = "E-mail inválido")
	private String email;

	public UsuarioDTO() {
	}

	public UsuarioDTO(Usuario usuario) {
		super();
		this.nome = usuario.getNome();
		this.sobreNome = usuario.getSobrenome();
		this.email = usuario.getEmail();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
