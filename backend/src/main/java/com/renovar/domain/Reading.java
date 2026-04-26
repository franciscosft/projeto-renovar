package com.renovar.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "measured_value")
    private Double value;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "recorded_at")
    private Date timestamp;
    private Double latitude;
    private Double longitude;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @ManyToOne
    @JoinColumn(name = "indicator_id")
    private Indicator indicator;

    public Reading(Integer id, Double value, Date timestamp, Coordinate coordinate, Device device, Indicator indicator) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
        this.latitude = coordinate.getLatitude();
        this.longitude = coordinate.getLongitude();
        this.device = device;
        this.indicator = indicator;
    }

    public Coordinate getCoordinate() {
        return new Coordinate(latitude, longitude);
    }

}