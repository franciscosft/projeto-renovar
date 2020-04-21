package com.renovar.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.renovar.domain.Coleta;
import com.renovar.domain.Coordenada;
import com.renovar.domain.Indicador;

public class ColetaRequisicaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Double medida;
	private Integer dispositivoId;
	private Integer indicadorId;

	public ColetaRequisicaoDTO() {
	}

	public ColetaRequisicaoDTO(Integer sensorId, Integer indicadorId, Double medida) {
		this.dispositivoId = sensorId;
		this.indicadorId = indicadorId;
		this.medida = medida;
	}
	

	public Double getMedida() {
		return medida;
	}

	public void setMedida(Double medida) {
		this.medida = medida;
	}

	public Integer getDispositivoId() {
		return dispositivoId;
	}

	public void setDispositivoId(Integer dispositivoId) {
		this.dispositivoId = dispositivoId;
	}

	public Integer getIndicadorId() {
		return indicadorId;
	}

	public void setIndicadorId(Integer indicadorId) {
		this.indicadorId = indicadorId;
	}

	@Override
	public String toString() {
		return "ColetaRequisicaoDTO [medida=" + medida + ", dispositivoId=" + dispositivoId + ", indicadorId=" + indicadorId + "]";
	}

}
