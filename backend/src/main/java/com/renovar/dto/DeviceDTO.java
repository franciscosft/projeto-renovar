package com.renovar.dto;

import java.util.List;

import com.renovar.domain.Coordinate;
import com.renovar.domain.Indicator;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DeviceDTO(
        Integer id,
        @NotEmpty(message = "Device name is required") String name,
        String trackingCode,
        Coordinate coordinate,
        @NotNull(message = "User id is required") Integer userId,
        String userEmail,
        List<Indicator> indicators
) {}