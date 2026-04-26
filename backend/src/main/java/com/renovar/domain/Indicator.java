package com.renovar.domain;

import java.io.Serializable;

import com.renovar.domain.enums.Unit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
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
public class Indicator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Integer unitId;

    private Double limit;

    public Indicator(Integer id, String name, Unit unit, Double limit) {
        this.id = id;
        this.name = name;
        this.unitId = unit.getId();
        this.limit = limit;
    }

    public Indicator(Integer id, String name, Unit unit) {
        this.id = id;
        this.name = name;
        this.unitId = unit.getId();
    }

    public String getUnit() {
        return Unit.fromId(unitId).getDescription();
    }

    public void setUnit(Unit unit) {
        this.unitId = unit.getId();
    }

}