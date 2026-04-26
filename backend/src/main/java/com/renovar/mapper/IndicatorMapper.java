package com.renovar.mapper;

import org.springframework.stereotype.Component;

import com.renovar.domain.Indicator;
import com.renovar.dto.IndicatorResponseDTO;

@Component
public class IndicatorMapper {

    public IndicatorResponseDTO toDTO(Indicator indicator) {
        return new IndicatorResponseDTO(
                indicator.getId(),
                indicator.getName(),
                indicator.getUnit(),
                indicator.getLimit()
        );
    }

}