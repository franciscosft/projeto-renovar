package com.renovar.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.renovar.dao.DeviceDAO;
import com.renovar.domain.Device;
import com.renovar.dto.DeviceDTO;
import com.renovar.services.exceptions.DataIntegrityException;
import com.renovar.services.exceptions.ObjectNotFoundException;

@Service
public class DeviceService {

    private final Logger log = LoggerFactory.getLogger(DeviceService.class);

    @Autowired
    private DeviceDAO dao;

    @Autowired
    private UserService userService;

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
        return new Device(dto.id(), dto.name(), dto.trackingCode(), dto.coordinate(), userService.findById(dto.userId()));
    }

    public Page<Device> findPage(Integer page, Integer pageSize, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Direction.valueOf(direction), orderBy);
        return dao.findAll(pageRequest);
    }

    public DeviceDTO toDeviceDTO(Device device) {
        log.info("Indicators: {}", device.getIndicators());
        return DeviceDTO.from(device);
    }

}