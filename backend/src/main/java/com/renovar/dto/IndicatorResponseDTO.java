package com.renovar.dto;

public record IndicatorResponseDTO(
        Integer id,
        String name,
        String unit,
        Double limit
) {}