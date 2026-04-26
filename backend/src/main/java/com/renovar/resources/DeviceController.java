package com.renovar.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.renovar.domain.Device;
import com.renovar.dto.DeviceDTO;
import com.renovar.services.DeviceService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(value = "/dispositivo")
public class DeviceController {

    private static Logger log = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private DeviceService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable Integer id) {
        Device device = service.findById(id);
        DeviceDTO dto = service.toDeviceDTO(device);
        return ResponseEntity.ok().body(dto);
    }

    @RequestMapping(value = "/todos", method = RequestMethod.GET)
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        log.info("Fetching all devices");
        List<Device> devices = service.findAll();
        List<DeviceDTO> dtos = devices.stream().map(service::toDeviceDTO).collect(Collectors.toList());
        return ResponseEntity.ok().body(dtos);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createDevice(@Valid @RequestBody DeviceDTO dto) {
        log.info("DeviceDTO: {}", dto);
        Device device = service.toDevice(dto);
        device = service.save(device);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(device.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateDevice(@RequestBody Device device, @PathVariable Integer id) {
        log.info("Device: {}", device);
        device.setId(id);
        device = service.update(device);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/pagina", method = RequestMethod.GET)
    public ResponseEntity<Page<DeviceDTO>> getPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Device> devices = service.findPage(page, pageSize, orderBy, direction);
        Page<DeviceDTO> dtos = devices.map(DeviceDTO::from);
        return ResponseEntity.ok().body(dtos);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteDevice(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}