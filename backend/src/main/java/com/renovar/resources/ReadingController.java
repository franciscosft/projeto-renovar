package com.renovar.resources;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.renovar.domain.Reading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.renovar.config.RabbitMQConfig;
import com.renovar.dto.ReadingRequestDTO;
import com.renovar.dto.ReadingResponseDTO;
import com.renovar.mapper.ReadingMapper;
import com.renovar.services.ReadingService;
import com.renovar.util.RenovarUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Readings", description = "Sensor measurement data collected from IoT devices")
@RestController
@RequestMapping("/reading")
public class ReadingController {

    private final Logger log = LoggerFactory.getLogger(ReadingController.class);

    @Autowired
    private ReadingService service;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ReadingMapper mapper;

    @Operation(summary = "Get reading by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reading found"),
        @ApiResponse(responseCode = "404", description = "Reading not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReadingResponseDTO> getReading(@PathVariable Integer id) {
        return ResponseEntity.ok(mapper.toDTO(service.findById(id)));
    }

    @Operation(summary = "Get all readings for a device")
    @ApiResponse(responseCode = "200", description = "List of readings ordered by timestamp descending")
    @CrossOrigin
    @GetMapping("/device/{deviceId}")
    public ResponseEntity<List<ReadingResponseDTO>> getReadingsByDevice(
            @Parameter(description = "Device ID") @PathVariable Integer deviceId) {
        List<Reading> readings = service.findByDeviceId(deviceId);
        List<ReadingResponseDTO> result = readings.stream().map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Get readings for a device filtered by indicator")
    @ApiResponse(responseCode = "200", description = "List of readings ordered by timestamp ascending")
    @CrossOrigin
    @GetMapping("/{deviceId}/{indicatorId}")
    public ResponseEntity<List<ReadingResponseDTO>> getReadingsByDeviceAndIndicator(
            @Parameter(description = "Device ID") @PathVariable Integer deviceId,
            @Parameter(description = "Indicator ID") @PathVariable Integer indicatorId) {
        List<Reading> readings = service.findByDeviceIdAndIndicatorId(deviceId, indicatorId);
        List<ReadingResponseDTO> result = readings.stream().map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Get readings within a date range",
               description = "Dates must be in yyyy-MM-dd format (e.g. 2024-01-15)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Readings within the specified range"),
        @ApiResponse(responseCode = "400", description = "Missing start or end date")
    })
    @CrossOrigin
    @GetMapping("/range")
    public ResponseEntity<List<ReadingResponseDTO>> getReadingsByDateRange(
            @Parameter(description = "Device ID") @RequestParam(value = "deviceId", defaultValue = "") Integer deviceId,
            @Parameter(description = "Indicator ID") @RequestParam(value = "indicatorId", defaultValue = "") Integer indicatorId,
            @Parameter(description = "Start date (yyyy-MM-dd)") @RequestParam(value = "startDate", defaultValue = "") String start,
            @Parameter(description = "End date (yyyy-MM-dd)") @RequestParam(value = "endDate", defaultValue = "") String end) {
        if (start.isEmpty() || end.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Date startDate = RenovarUtils.toStartDate(start);
        Date endDate = RenovarUtils.toEndDate(end);
        log.info("Fetching readings between {} and {}", startDate, endDate);
        List<Reading> readings = service.findByDeviceIdAndIndicatorIdBetweenDates(deviceId, indicatorId, startDate, endDate);
        List<ReadingResponseDTO> result = readings.stream().map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Get readings for a device paginated")
    @GetMapping
    public ResponseEntity<Page<ReadingResponseDTO>> getPage(
            @RequestParam(value = "deviceId", defaultValue = "") Integer deviceId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "orderBy", defaultValue = "recorded_at") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        Page<Reading> readings = service.findByDeviceIdPaged(deviceId, page, pageSize, orderBy, direction);
        Page<ReadingResponseDTO> dtos = readings.map(mapper::toDTO);
        return ResponseEntity.ok().body(dtos);
    }

    @Operation(summary = "Get all readings (all devices)")
    @GetMapping("/all")
    public ResponseEntity<List<ReadingResponseDTO>> getAll() {
        List<Reading> readings = service.findAll();
        List<ReadingResponseDTO> dtos = readings.stream().map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok().body(dtos);
    }

    @Operation(summary = "Get the most recent reading for a device")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Latest reading found"),
        @ApiResponse(responseCode = "404", description = "No readings found for this device")
    })
    @GetMapping("/device/{deviceId}/latest")
    public ResponseEntity<ReadingResponseDTO> getLastDeviceReading(
            @Parameter(description = "Device ID") @PathVariable Integer deviceId) {
        return ResponseEntity.ok(mapper.toDTO(service.getLastDeviceReading(deviceId)));
    }

    @Operation(summary = "Submit a new reading from a device")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Reading created"),
        @ApiResponse(responseCode = "404", description = "Device or indicator not found")
    })
    @PostMapping
    public ResponseEntity<Void> createReading(@RequestBody ReadingRequestDTO dto) {
        Reading reading = service.toReading(dto);
        reading = service.save(reading);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(reading.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Asynchronous reading ingestion via RabbitMQ",
               description = "Publishes the reading to the queue and returns immediately. The consumer persists it asynchronously.")
    @ApiResponse(responseCode = "202", description = "Reading accepted for async processing")
    @PostMapping("/ingest")
    public ResponseEntity<Void> ingestReading(@RequestBody ReadingRequestDTO dto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, dto);
        log.info("Reading queued for device {}, indicator {}", dto.deviceId(), dto.indicatorId());
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Test endpoint — echoes the received payload to the log")
    @PostMapping("/test")
    public ResponseEntity<Void> test(@RequestBody ReadingRequestDTO dto) {
        log.info("Test parameter: {}", dto);
        return ResponseEntity.noContent().build();
    }

}