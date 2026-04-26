package com.renovar.mapper;

import org.springframework.stereotype.Component;

import com.renovar.domain.Indicator;
import com.renovar.domain.Reading;
import com.renovar.dto.ReadingResponseDTO;

@Component
public class ReadingMapper {

    public ReadingResponseDTO toDTO(Reading reading) {
        Indicator indicator = reading.getIndicator();
        return new ReadingResponseDTO(
                reading.getId(),
                reading.getDevice().getName(),
                indicator.getName(),
                reading.getValue(),
                indicator.getUnit(),
                reading.getTimestamp().getTime()
        );
    }

}