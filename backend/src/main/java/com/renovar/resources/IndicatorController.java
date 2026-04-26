package com.renovar.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.renovar.domain.Indicator;
import com.renovar.dto.IndicatorResponseDTO;
import com.renovar.services.IndicatorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Indicators", description = "Measurement types tracked by devices (e.g. CO, temperature, pressure)")
@RestController
@RequestMapping("/indicators")
public class IndicatorController {

    @Autowired
    private IndicatorService service;

    @Operation(summary = "Get indicator by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Indicator found"),
        @ApiResponse(responseCode = "404", description = "Indicator not found")
    })
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<IndicatorResponseDTO> getIndicator(
            @Parameter(description = "Indicator ID") @PathVariable Integer id) {
        return ResponseEntity.ok(IndicatorResponseDTO.from(service.findById(id)));
    }

    @Operation(summary = "Get all indicators")
    @ApiResponse(responseCode = "200", description = "List of all registered indicators")
    @GetMapping
    public ResponseEntity<List<IndicatorResponseDTO>> getIndicators() {
        List<IndicatorResponseDTO> dtos = service.findAll().stream()
                .map(IndicatorResponseDTO::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Create a new indicator")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Indicator created"),
        @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public ResponseEntity<Void> createIndicator(@RequestBody Indicator indicator) {
        indicator = service.save(indicator);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(indicator.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Update an indicator")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Indicator updated"),
        @ApiResponse(responseCode = "404", description = "Indicator not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateIndicator(
            @RequestBody Indicator indicator,
            @Parameter(description = "Indicator ID") @PathVariable Integer id) {
        indicator.setId(id);
        service.update(indicator);
        return ResponseEntity.noContent().build();
    }

}