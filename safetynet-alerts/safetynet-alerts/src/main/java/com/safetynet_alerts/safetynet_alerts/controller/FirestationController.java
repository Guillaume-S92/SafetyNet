package com.safetynet_alerts.safetynet_alerts.controller;

import com.safetynet_alerts.safetynet_alerts.dto.FirestationResponse;
import com.safetynet_alerts.safetynet_alerts.service.FirestationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirestationController {

    private static final Logger logger = LoggerFactory.getLogger(FirestationController.class);

    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @GetMapping("/firestation")
    public ResponseEntity<FirestationResponse> getPersonsByStation(@RequestParam String stationNumber) {
        logger.info("Request received for stationNumber: {}", stationNumber);
        FirestationResponse response = firestationService.getPersonsByStation(stationNumber);
        logger.info("Response: {}", response);
        return ResponseEntity.ok(response);
    }
}
