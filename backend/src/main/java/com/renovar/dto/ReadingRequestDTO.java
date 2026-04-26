package com.renovar.dto;

public record ReadingRequestDTO(
        Double value,
        Integer deviceId,
        Integer indicatorId
) {}