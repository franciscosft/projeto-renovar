package com.renovar.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.renovar.dao.ReadingDAO;
import com.renovar.domain.Coordinate;
import com.renovar.domain.Device;
import com.renovar.domain.Indicator;
import com.renovar.domain.Reading;
import com.renovar.dto.ReadingRequestDTO;
import com.renovar.services.exceptions.ObjectNotFoundException;

@Slf4j
@Service
@AllArgsConstructor
public class ReadingService {

    private final ReadingDAO dao;
    private final DeviceService deviceService;
    private final IndicatorService indicatorService;

    public Reading findById(Integer readingId) {
        Optional<Reading> result = dao.findById(readingId);
        return result.orElseThrow(() -> new ObjectNotFoundException(
                "Reading not found: " + readingId + ", Type: " + Reading.class.getName()));
    }

    public List<Reading> findByDeviceId(Integer deviceId) {
        return dao.findByDeviceId(deviceId);
    }

    public List<Reading> findByDeviceIdAndIndicatorId(Integer deviceId, Integer indicatorId) {
        return dao.findByDeviceIdAndIndicatorId(deviceId, indicatorId);
    }

    public List<Reading> findByDeviceIdAndIndicatorIdBetweenDates(Integer deviceId, Integer indicatorId,
            Date startDate, Date endDate) {
        log.info("Start: {} End: {}", startDate, endDate);
        return dao.findByDeviceIdAndIndicatorIdBetweenDates(deviceId, indicatorId, startDate, endDate);
    }

    public Page<Reading> findByDeviceIdPaged(Integer deviceId, Integer page, Integer pageSize,
            String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Direction.valueOf(direction), orderBy);
        return dao.findByDeviceIdPaged(deviceId, pageRequest);
    }

    public Reading getLastDeviceReading(Integer deviceId) {
        return findByDeviceId(deviceId).get(0);
    }

    public Page<Reading> findPage(Integer page, Integer pageSize, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Direction.valueOf(direction), orderBy);
        return dao.findAll(pageRequest);
    }

    public List<Reading> findAll() {
        return dao.findAll();
    }

    @Transactional
    public Reading save(Reading reading) {
        return dao.save(reading);
    }

    public Reading toReading(ReadingRequestDTO dto) {
        Date now = new Date();
        Device device = deviceService.findById(dto.deviceId());
        Indicator indicator = indicatorService.findById(dto.indicatorId());
        Coordinate coordinate = new Coordinate(device.getLatitude(), device.getLongitude());
        Reading reading = new Reading(null, dto.value(), now, coordinate, device, indicator);
        log.info("Reading to be saved: {}", reading);
        return reading;
    }

}