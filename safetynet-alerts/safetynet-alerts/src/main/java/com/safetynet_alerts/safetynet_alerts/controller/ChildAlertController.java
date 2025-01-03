package com.safetynet_alerts.safetynet_alerts.controller;

import com.safetynet_alerts.safetynet_alerts.dto.ChildAlertResponse;
import com.safetynet_alerts.safetynet_alerts.service.ChildAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChildAlertController {

    private static final Logger logger = LoggerFactory.getLogger(ChildAlertController.class);
    private final ChildAlertService childAlertService;

    public ChildAlertController(ChildAlertService childAlertService) {
        this.childAlertService = childAlertService;
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertResponse> getChildAlert(@RequestParam String address) {
        logger.info("Request received for childAlert at address: {}", address);
        ChildAlertResponse response = childAlertService.getChildAlertByAddress(address);
        logger.info("Response: {}", response);
        return ResponseEntity.ok(response);
    }
}
