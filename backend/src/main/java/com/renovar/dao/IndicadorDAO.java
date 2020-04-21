package com.renovar.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renovar.domain.Indicador;

@Repository
public interface IndicadorDAO extends JpaRepository<Indicador, Integer> {

}
