package com.renovar.domain;

import java.io.Serializable;

import com.renovar.domain.enums.Unidade;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
public class Indicador implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;

	// getter e setter customizados: converte entre Integer e Unidade
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Integer unidade;

	private Double limite;

	public Indicador(Integer id, String nome, Unidade unidade, Double limite) {
		this.id = id;
		this.nome = nome;
		this.unidade = unidade.getId();
		this.limite = limite;
	}

	public Indicador(Integer id, String nome, Unidade unidade) {
		this.id = id;
		this.nome = nome;
		this.unidade = unidade.getId();
	}

	public String getUnidade() {
		return Unidade.toUnidade(unidade).getDescricaoUnidade();
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade.getId();
	}

}
