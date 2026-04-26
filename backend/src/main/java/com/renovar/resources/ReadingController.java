package com.renovar.resources;

import java.net.URI;
import java.util.Date;
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

import com.renovar.domain.Reading;
import com.renovar.dto.ReadingRequestDTO;
import com.renovar.dto.ReadingResponseDTO;
import com.renovar.services.ReadingService;
import com.renovar.util.RenovarUtils;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/coletas")
public class ReadingController {

    private final Logger log = LoggerFactory.getLogger(ReadingController.class);

    @Autowired
    private ReadingService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Reading> getReading(@PathVariable Integer id) {
        Reading reading = service.findById(id);
        return ResponseEntity.ok().body(reading);
    }

    @CrossOrigin
    @RequestMapping(value = "/dispositivo/{deviceId}", method = RequestMethod.GET)
    public ResponseEntity<List<ReadingResponseDTO>> getReadingsByDevice(@PathVariable Integer deviceId) {
        List<Reading> readings = service.findByDeviceId(deviceId);
        List<ReadingResponseDTO> result = readings.stream().map(ReadingResponseDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok().body(result);
    }

    @CrossOrigin
    @RequestMapping(value = "/{deviceId}/{indicatorId}", method = RequestMethod.GET)
    public ResponseEntity<List<ReadingResponseDTO>> getReadingsByDeviceAndIndicator(
            @PathVariable Integer deviceId,
            @PathVariable Integer indicatorId) {
        List<Reading> readings = service.findByDeviceIdAndIndicatorId(deviceId, indicatorId);
        List<ReadingResponseDTO> result = readings.stream().map(ReadingResponseDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok().body(result);
    }

    @CrossOrigin
    @RequestMapping(value = "/intervalo/", method = RequestMethod.GET)
    public ResponseEntity<List<ReadingResponseDTO>> getReadingsByDateRange(
            @RequestParam(value = "idDispositivo", defaultValue = "") Integer deviceId,
            @RequestParam(value = "idIndicador", defaultValue = "") Integer indicatorId,
            @RequestParam(value = "dataInicio", defaultValue = "") String start,
            @RequestParam(value = "dataFim", defaultValue = "") String end) {
        if (start.isEmpty() || end.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Date startDate = RenovarUtils.toStartDate(start);
        Date endDate = RenovarUtils.toEndDate(end);
        log.info("Fetching readings between {} and {}", startDate, endDate);
        List<Reading> readings = service.findByDeviceIdAndIndicatorIdBetweenDates(deviceId, indicatorId, startDate, endDate);
        List<ReadingResponseDTO> result = readings.stream().map(ReadingResponseDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ReadingResponseDTO>> getPage(
            @RequestParam(value = "idDispositivo", defaultValue = "") Integer deviceId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "orderBy", defaultValue = "timestamp") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        Page<Reading> readings = service.findByDeviceIdPaged(deviceId, page, pageSize, orderBy, direction);
        Page<ReadingResponseDTO> dtos = readings.map(ReadingResponseDTO::from);
        return ResponseEntity.ok().body(dtos);
    }

    @RequestMapping(value = "/todas", method = RequestMethod.GET)
    public ResponseEntity<List<ReadingResponseDTO>> getAll() {
        List<Reading> readings = service.findAll();
        List<ReadingResponseDTO> dtos = readings.stream().map(ReadingResponseDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok().body(dtos);
    }

    @RequestMapping(value = "/dispositivo/ultima/{deviceId}", method = RequestMethod.GET)
    public ResponseEntity<Reading> getLastDeviceReading(@PathVariable Integer deviceId) {
        Reading reading = service.getLastDeviceReading(deviceId);
        return ResponseEntity.ok().body(reading);
    }

    @Operation(summary = "Add a new reading")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createReading(@RequestBody ReadingRequestDTO dto) {
        Reading reading = service.toReading(dto);
        reading = service.save(reading);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(reading.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/teste", method = RequestMethod.POST)
    public ResponseEntity<Void> test(@RequestBody ReadingRequestDTO dto) {
        log.info("Test parameter: {}", dto);
        return ResponseEntity.noContent().build();
    }

}