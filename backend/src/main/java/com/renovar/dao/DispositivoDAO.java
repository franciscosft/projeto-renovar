package com.renovar.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renovar.domain.Dispositivo;

@Repository
public interface DispositivoDAO extends JpaRepository<Dispositivo, Integer> {

}
