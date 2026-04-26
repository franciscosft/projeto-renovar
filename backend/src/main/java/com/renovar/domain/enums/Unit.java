package com.renovar.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Unit {

    TEMPERATURE(1, "degrees celsius"),
    PRESSURE(2, "pascal"),
    CONCENTRATION(3, "ug/m3");

    private final Integer id;
    private final String description;

    public static Unit fromId(Integer id) {
        if (id == null) {
            return null;
        }
        for (Unit unit : Unit.values()) {
            if (unit.id.equals(id)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid id: " + id);
    }

}