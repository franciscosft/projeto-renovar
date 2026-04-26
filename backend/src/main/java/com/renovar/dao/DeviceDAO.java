package com.renovar.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renovar.domain.Device;

@Repository
public interface DeviceDAO extends JpaRepository<Device, Integer> {

}