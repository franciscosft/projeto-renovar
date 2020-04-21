package com.renovar.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.renovar.dto.DispositivoDTO;

@Entity
public class Dispositivo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String codigoRastreio;
	private Double latitude;
	private Double longitude;

	// Implementar a deleção em cascade
	@ManyToMany
	@JoinTable(name = "DISPOSITVO_INDICADOR", joinColumns = @JoinColumn(name = "dispositvo_id"), inverseJoinColumns = @JoinColumn(name = "indicador_id"))
	private List<Indicador> indicadores = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	public Dispositivo() {
	}

	public Dispositivo(Integer id, String nome, String codigoRastreio, Coordenada coordenada, Usuario usuario) {
		super();
		this.id = id;
		this.nome = nome;
		this.codigoRastreio = codigoRastreio;
		this.latitude = coordenada.getLatitude();
		this.longitude = coordenada.getLongitude();
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigoRastreio() {
		return codigoRastreio;
	}

	public void setCodigoRastreio(String codigoRastreio) {
		this.codigoRastreio = codigoRastreio;
	}

	@Override
	public String toString() {
		return "Dispositivo [id=" + id + ", nome=" + nome + ", codigoRastreio=" + codigoRastreio + ", latitude="
				+ latitude + ", longitude=" + longitude + ", indicadores=" + indicadores + ", usuario=" + usuario + "]";
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
		Dispositivo other = (Dispositivo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
