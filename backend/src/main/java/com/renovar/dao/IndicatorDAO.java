package com.renovar.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renovar.domain.Indicator;

@Repository
public interface IndicatorDAO extends JpaRepository<Indicator, Integer> {

}