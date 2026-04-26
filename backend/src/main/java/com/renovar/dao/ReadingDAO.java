package com.renovar.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.renovar.domain.Reading;

@Repository
public interface ReadingDAO extends JpaRepository<Reading, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Reading obj WHERE obj.device.id = :id ORDER BY obj.timestamp DESC")
    List<Reading> findByDeviceId(@Param("id") Integer id);

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Reading obj WHERE obj.device.id = :id ORDER BY obj.timestamp DESC")
    Page<Reading> findByDeviceIdPaged(@Param("id") Integer id, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Reading obj WHERE obj.device.id = :deviceId AND obj.indicator.id = :indicatorId ORDER BY obj.timestamp ASC")
    List<Reading> findByDeviceIdAndIndicatorId(@Param("deviceId") Integer deviceId,
            @Param("indicatorId") Integer indicatorId);

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Reading obj WHERE obj.device.id = :deviceId AND obj.indicator.id = :indicatorId ORDER BY obj.timestamp ASC")
    Page<Reading> findByDeviceIdAndIndicatorIdPaged(@Param("deviceId") Integer deviceId,
            @Param("indicatorId") Integer indicatorId, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Reading obj WHERE obj.device.id = :deviceId AND obj.indicator.id = :indicatorId AND obj.timestamp BETWEEN :startDate AND :endDate ORDER BY obj.timestamp ASC")
    List<Reading> findByDeviceIdAndIndicatorIdBetweenDates(@Param("deviceId") Integer deviceId,
            @Param("indicatorId") Integer indicatorId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

}