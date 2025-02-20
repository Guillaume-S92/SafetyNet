package com.safetynet_alerts.safetynet_alerts.controller;

import com.safetynet_alerts.safetynet_alerts.dto.FireResponse;
import com.safetynet_alerts.safetynet_alerts.service.FireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireController {

    private static final Logger logger = LoggerFactory.getLogger(FireController.class);
    private final FireService fireService;

    public FireController(FireService fireService) {
        this.fireService = fireService;
    }

    @GetMapping("/fire")
    public ResponseEntity<FireResponse> getFireByAddress(@RequestParam String address) {
        logger.info("Request received for fire at address: {}", address);
        FireResponse response = fireService.getFireByAddress(address);
        logger.info("Response: {}", response);
        return ResponseEntity.ok(response);
    }
}
