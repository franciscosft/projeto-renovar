package com.renovar.domain.enums;

public enum Unit {

    TEMPERATURE(1, "degrees celsius"),
    PRESSURE(2, "pascal"),
    CONCENTRATION(3, "ug/m3");

    private Integer id;
    private String description;

    private Unit(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static Unit fromId(Integer id) {
        if (id == null) {
            return null;
        }
        for (Unit unit : Unit.values()) {
            if (unit.getId().equals(id)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid id: " + id);
    }

}
