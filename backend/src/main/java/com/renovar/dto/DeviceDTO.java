package com.renovar.dto;

import java.util.List;

import com.renovar.domain.Coordinate;
import com.renovar.domain.Device;
import com.renovar.domain.Indicator;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DeviceDTO(
        Integer id,
        @NotEmpty(message = "Device name is required") String name,
        String trackingCode,
        Coordinate coordinate,
        @NotNull(message = "User id is required") Integer userId,
        String user,
        List<Indicator> indicators
) {
    public static DeviceDTO from(Device device) {
        return new DeviceDTO(
                device.getId(),
                device.getName(),
                device.getTrackingCode(),
                new Coordinate(device.getLatitude(), device.getLongitude()),
                device.getUser().getId(),
                device.getUser().getName(),
                device.getIndicators()
        );
    }
}