package com.renovar.dto;

public record ReadingResponseDTO(
        Integer id,
        String device,
        String indicatorName,
        Double value,
        String unit,
        Long timestamp
) {}