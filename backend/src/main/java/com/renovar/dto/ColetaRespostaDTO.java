package com.renovar.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.renovar.domain.Coleta;
import com.renovar.domain.Coordenada;
import com.renovar.domain.Indicador;

public class ColetaRespostaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String dispositivo;
	private String indicadorNome;
	private Double medida;
	private String unidade;

//	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Long data;
//	private Coordenada coordenada;

	public ColetaRespostaDTO() {
	}

	// Utilizar para o get
	public ColetaRespostaDTO(Coleta coleta) {
		super();
		id = coleta.getId();
		data = coleta.getData().getTime();
		medida = coleta.getMedida();
		dispositivo = coleta.getDispositivo().getNome();
		Indicador indicador = coleta.getIndicador();
		indicadorNome = indicador.getNome();
		unidade = indicador.getUnidade();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDispositivo() {
		return dispositivo;
	}

	public void setSensor(String sensor) {
		this.dispositivo = sensor;
	}

	public String getIndicadorNome() {
		return indicadorNome;
	}

	public void setIndicadorNome(String indicadorNome) {
		this.indicadorNome = indicadorNome;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public Long getData() {
		return data;
	}

	public void setData(Long data) {
		this.data = data;
	}

	public Double getMedida() {
		return medida;
	}

	public void setMedida(Double medida) {
		this.medida = medida;
	}

//	public Coordenada getCoordenada() {
//		return coordenada;
//	}
//
//	public void setCoordenada(Coordenada coordenada) {
//		this.coordenada = coordenada;
//	}

}
