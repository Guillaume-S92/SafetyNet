package com.safetynet_alerts.safetynet_alerts.controller;

import com.safetynet_alerts.safetynet_alerts.dto.FloodStationsResponse;
import com.safetynet_alerts.safetynet_alerts.service.FloodStationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FloodStationsController {

    private static final Logger logger = LoggerFactory.getLogger(FloodStationsController.class);
    private final FloodStationsService floodStationsService;

    public FloodStationsController(FloodStationsService floodStationsService) {
        this.floodStationsService = floodStationsService;
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<List<FloodStationsResponse>> getFloodStations(@RequestParam List<String> stations) {
        logger.info("Request received for flood/stations with stations: {}", stations);
        List<FloodStationsResponse> response = floodStationsService.getFloodStations(stations);
        logger.info("Response: {}", response);
        return ResponseEntity.ok(response);
    }
}
