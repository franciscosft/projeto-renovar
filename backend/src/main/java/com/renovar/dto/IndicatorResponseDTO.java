package com.renovar.dto;

import com.renovar.domain.Indicator;

public record IndicatorResponseDTO(
        Integer id,
        String name,
        String unit,
        Double limit
) {
    public static IndicatorResponseDTO from(Indicator indicator) {
        return new IndicatorResponseDTO(
                indicator.getId(),
                indicator.getName(),
                indicator.getUnit(),
                indicator.getLimit()
        );
    }
}