package com.renovar.services;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import com.renovar.dao.DeviceDAO;
import com.renovar.dao.IndicatorDAO;
import com.renovar.domain.Device;
import com.renovar.domain.Indicator;
import com.renovar.dto.DeviceDTO;
import com.renovar.mapper.DeviceMapper;
import com.renovar.services.exceptions.DataIntegrityException;
import com.renovar.services.exceptions.ObjectNotFoundException;

@Slf4j
@Service
@AllArgsConstructor
public class DeviceService {

    private final DeviceDAO dao;

    private final UserService userService;

    private final IndicatorDAO indicatorDAO;

    private final DeviceMapper deviceMapper;

    public Device findById(Integer id) {
        Optional<Device> result = dao.findById(id);
        return result.orElseThrow(() -> new ObjectNotFoundException(
                "Device not found: " + id + ", Type: " + Device.class.getName()));
    }

    public List<Device> findAll() {
        return dao.findAll();
    }

    public Device save(Device device) {
        return dao.save(device);
    }

    public Device update(Device device) {
        Device existing = findById(device.getId());
        existing.setName(device.getName());
        return dao.save(existing);
    }

    public void delete(Integer id) {
        findById(id);
        try {
            dao.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Cannot delete a device that has dependent entities", e);
        }
    }

    public Device toDevice(DeviceDTO dto) {
        log.debug("DeviceDTO: {}", dto);
        Device device = new Device(dto.id(), dto.name(), dto.coordinate(), userService.findById(dto.userId()));
        if (dto.indicators() != null && !dto.indicators().isEmpty()) {
            List<Integer> ids = dto.indicators().stream()
                    .map(Indicator::getId)
                    .collect(Collectors.toList());
            device.getIndicators().addAll(indicatorDAO.findAllById(ids));
        }
        return device;
    }

    public Page<Device> findPage(Integer page, Integer pageSize, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Direction.valueOf(direction), orderBy);
        return dao.findAll(pageRequest);
    }

    public DeviceDTO toDeviceDTO(Device device) {
        log.info("Indicators: {}", device.getIndicators());
        return deviceMapper.toDTO(device);
    }

}