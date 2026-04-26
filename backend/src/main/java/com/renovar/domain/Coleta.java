package com.renovar.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Coleta implements Serializable {

	private static final long serialVersionUID = -8861764815033131944L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Double medida;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date data;
	private Double latitude;
	private Double longitude;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "dispositivo_id")
	private Dispositivo dispositivo;

	@ManyToOne
	@JoinColumn(name = "indicador_id")
	private Indicador indicador;

	public Coleta(Integer id, Double medida, Date data, Coordenada coordenada, Dispositivo dispositivo,
			Indicador indicador) {
		this.id = id;
		this.medida = medida;
		this.data = data;
		this.latitude = coordenada.getLatitude();
		this.longitude = coordenada.getLongitude();
		this.dispositivo = dispositivo;
		this.indicador = indicador;
	}

	public Coordenada getCoordenada() {
		return new Coordenada(latitude, longitude);
	}

}
