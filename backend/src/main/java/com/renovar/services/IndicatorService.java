package com.renovar.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renovar.dao.IndicatorDAO;
import com.renovar.domain.Indicator;
import com.renovar.services.exceptions.ObjectNotFoundException;

@Service
public class IndicatorService {

    @Autowired
    private IndicatorDAO dao;

    public Indicator findById(Integer id) {
        Optional<Indicator> result = dao.findById(id);
        return result.orElseThrow(() -> new ObjectNotFoundException(
                "Indicator not found: " + id + ", Type: " + Indicator.class.getName()));
    }

    public List<Indicator> findAll() {
        return dao.findAll();
    }

    public Indicator save(Indicator indicator) {
        indicator.setId(null);
        return dao.save(indicator);
    }

    public List<Indicator> toIndicators(List<Integer> indicatorIds) {
        List<Indicator> indicators = new ArrayList<>();
        for (Integer id : indicatorIds) {
            indicators.add(findById(id));
        }
        return indicators;
    }

    public Indicator update(Indicator indicator) {
        return null;
    }

}