package com.renovar.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
public class Dispositivo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String codigoRastreio;
	private Double latitude;
	private Double longitude;

	@ManyToMany
	@JoinTable(name = "DISPOSITVO_INDICADOR",
		joinColumns = @JoinColumn(name = "dispositvo_id"),
		inverseJoinColumns = @JoinColumn(name = "indicador_id"))
	private List<Indicador> indicadores = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	public Dispositivo(Integer id, String nome, String codigoRastreio, Coordenada coordenada, Usuario usuario) {
		this.id = id;
		this.nome = nome;
		this.codigoRastreio = codigoRastreio;
		this.latitude = coordenada.getLatitude();
		this.longitude = coordenada.getLongitude();
		this.usuario = usuario;
	}

}
