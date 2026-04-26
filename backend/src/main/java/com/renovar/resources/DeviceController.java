package com.renovar.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.renovar.domain.Device;
import com.renovar.dto.DeviceDTO;
import com.renovar.mapper.DeviceMapper;
import com.renovar.services.DeviceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Devices", description = "IoT devices that collect environmental measurements")
@CrossOrigin
@RestController
@RequestMapping("/devices")
@AllArgsConstructor
public class DeviceController {

    private static Logger log = LoggerFactory.getLogger(DeviceController.class);

    private final DeviceService service;

    private final DeviceMapper mapper;

    @Operation(summary = "Get device by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Device found"),
        @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> getDevice(
            @Parameter(description = "Device ID") @PathVariable Integer id) {
        return ResponseEntity.ok(mapper.toDTO(service.findById(id)));
    }

    @Operation(summary = "Get all devices")
    @ApiResponse(responseCode = "200", description = "List of all registered devices")
    @GetMapping("/all")
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        log.info("Fetching all devices");
        List<DeviceDTO> dtos = service.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Register a new device")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Device created"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping
    public ResponseEntity<Void> createDevice(@Valid @RequestBody DeviceDTO dto) {
        log.info("DeviceDTO: {}", dto);
        Device device = service.toDevice(dto);
        device = service.save(device);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(device.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Update a device's name")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Device updated"),
        @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDevice(
            @RequestBody Device device,
            @Parameter(description = "Device ID") @PathVariable Integer id) {
        log.info("Device: {}", device);
        device.setId(id);
        service.update(device);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get devices paginated")
    @GetMapping("/page")
    public ResponseEntity<Page<DeviceDTO>> getPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<DeviceDTO> dtos = service.findPage(page, pageSize, orderBy, direction).map(mapper::toDTO);
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Delete a device")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Device deleted"),
        @ApiResponse(responseCode = "400", description = "Cannot delete device with associated readings"),
        @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(
            @Parameter(description = "Device ID") @PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}