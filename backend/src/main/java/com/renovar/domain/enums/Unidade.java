package com.renovar.domain.enums;

public enum Unidade {

						TEMPERATURA(1, "graus celcius"),
						PRESSAO(2, "pascal"),
						CONCENTRACAO(3, "ug/m3");

	private Integer id;
	private String unidade;

	private Unidade(Integer id, String unidade) {
		this.id = id;
		this.unidade = unidade;
	}

	public Integer getId() {
		return id;
	}

	public String getDescricaoUnidade() {
		return unidade;
	}

	public static Unidade toUnidade(Integer id) {
		if (id == null) {
			return null;
		}

		for (Unidade unidade : Unidade.values()) {
			if (unidade.getId().equals(id)) {
				return unidade;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + id);
	}

}
