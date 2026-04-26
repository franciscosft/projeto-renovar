package com.renovar.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.renovar.domain.Indicator;
import com.renovar.services.IndicatorService;

@RestController
@RequestMapping(value = "/indicadores")
public class IndicatorController {

    @Autowired
    private IndicatorService service;

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getIndicator(@PathVariable Integer id) {
        Indicator indicator = service.findById(id);
        return ResponseEntity.ok().body(indicator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getIndicators() {
        List<Indicator> indicators = service.findAll();
        return ResponseEntity.ok().body(indicators);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createIndicator(@RequestBody Indicator indicator) {
        indicator = service.save(indicator);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(indicator.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateIndicator(@RequestBody Indicator indicator, @PathVariable Integer id) {
        indicator.setId(id);
        indicator = service.update(indicator);
        return ResponseEntity.noContent().build();
    }

}