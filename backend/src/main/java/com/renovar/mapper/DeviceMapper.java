package com.renovar.mapper;

import org.springframework.stereotype.Component;

import com.renovar.domain.Coordinate;
import com.renovar.domain.Device;
import com.renovar.dto.DeviceDTO;

@Component
public class DeviceMapper {

    public DeviceDTO toDTO(Device device) {
        return new DeviceDTO(
                device.getId(),
                device.getName(),
                device.getTrackingCode(),
                new Coordinate(device.getLatitude(), device.getLongitude()),
                device.getUser().getId(),
                device.getUser().getEmail(),
                device.getIndicators()
        );
    }

}