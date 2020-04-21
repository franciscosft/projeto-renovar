package com.renovar.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.renovar.domain.enums.Unidade;

@Entity
public class Indicador implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Integer unidade;
	private Double limite;
	//TODO Incluir parametro de limite

	public Indicador() {
	}

	public Indicador(Integer id, String nome, Unidade unidade, Double limite) {
		this();
		this.id = id;
		this.nome = nome;
		this.unidade = unidade.getId();
		this.limite = limite;
	}
	
	public Indicador(Integer id, String nome, Unidade unidade) {
		this();
		this.id = id;
		this.nome = nome;
		this.unidade = unidade.getId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUnidade() {
		return Unidade.toUnidade(unidade).getDescricaoUnidade();
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade.getId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Indicador other = (Indicador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Indicador [id=" + id + ", nome=" + nome + ", unidade=" + unidade + "]";
	}

	public Double getLimite() {
		return limite;
	}

	public void setLimite(Double limite) {
		this.limite = limite;
	}

}
