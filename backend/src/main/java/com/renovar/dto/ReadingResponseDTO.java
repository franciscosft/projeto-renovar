package com.renovar.dto;

import com.renovar.domain.Indicator;
import com.renovar.domain.Reading;

public record ReadingResponseDTO(
        Integer id,
        String device,
        String indicatorName,
        Double value,
        String unit,
        Long timestamp
) {
    public static ReadingResponseDTO from(Reading reading) {
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