package com.renovar.domain;

import java.io.Serializable;

public class Coordenada implements Serializable {

	private static final long serialVersionUID = 1L;
	private Double latitude;
	private Double longitude;

	public Coordenada() {
	}

	public Coordenada(Double latitude, Double longitude) {
		this.setLatitude(latitude);
		this.setLongitude(longitude);
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

	@Override
	public String toString() {
		return "Coordenada [latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
