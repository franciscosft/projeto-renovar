package com.renovar.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.renovar.domain.Coordenada;
import com.renovar.domain.Dispositivo;
import com.renovar.domain.Indicador;

public class DispositivoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotEmpty(message = "Preencha o nome do sensor para adicionar")
	private String nome;
	private String codigoRastreio;
	private Coordenada coordenada;
	@NotNull(message = "Preencha o identificador do usuário que tem o sensor")
	private Integer usuarioId;
	private String usuario;
	private List<Indicador> indicadores;

	public DispositivoDTO() {
	}

	public DispositivoDTO(Dispositivo dispositivo) {
		super();
		this.id = dispositivo.getId();
		this.nome = dispositivo.getNome();
		this.codigoRastreio = dispositivo.getCodigoRastreio();
		this.coordenada = new Coordenada(dispositivo.getLatitude(), dispositivo.getLongitude());
		this.usuario = dispositivo.getUsuario().getNome();
		this.usuarioId = dispositivo.getUsuario().getId();
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

	public String getCodigoRastreio() {
		return codigoRastreio;
	}

	public void setCodigoRastreio(String codigoRastreio) {
		this.codigoRastreio = codigoRastreio;
	}

	public Coordenada getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	@Override
	public String toString() {
		return "DispostivoDTO [id=" + id + ", nome=" + nome + ", codigoRastreio=" + codigoRastreio + ", coordenada="
				+ coordenada + ", usuarioId=" + usuarioId + ", usuario=" + usuario + "]";
	}

	public List<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

}
