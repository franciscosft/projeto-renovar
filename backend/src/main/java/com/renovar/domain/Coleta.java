package com.renovar.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Coleta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Double medida;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date data;
	private Double latitude;
	private Double longitude;

	@JsonIgnore // utilizado para esconder os objetos Json
	@ManyToOne
	@JoinColumn(name = "dispositivo_id")
	private Dispositivo dispositivo;

	@ManyToOne
	@JoinColumn(name = "indicador_id")
	private Indicador indicador;

	public Coleta() {
	}

	public Coleta(Integer id, Double medida, Date data, Coordenada coordenada, Dispositivo dispositivo,
			Indicador indicador) {
		super();
		this.id = id;
		this.medida = medida;
		this.data = data;
		this.latitude = coordenada.getLatitude();
		this.longitude = coordenada.getLongitude();
		this.dispositivo = dispositivo;
		this.indicador = indicador;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getMedida() {
		return medida;
	}

	public void setMedida(Double medida) {
		this.medida = medida;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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
		Coleta other = (Coleta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Dispositivo getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(Dispositivo sensor) {
		this.dispositivo = sensor;
	}

	@Override
	public String toString() {
		return "Coleta [id=" + id + ", medida=" + medida + ", data=" + data + ", latitude=" + latitude + ", longitude="
				+ longitude + ", dispositivo=" + dispositivo + ", indicador=" + indicador + "]";
	}

	public Indicador getIndicador() {
		return indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Coordenada getCoordenada() {
		return new Coordenada(getLatitude(), getLongitude());
	}

}
