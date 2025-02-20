package com.safetynet_alerts.safetynet_alerts.controller;

import com.safetynet_alerts.safetynet_alerts.service.PhoneAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhoneAlertController {

    private static final Logger logger = LoggerFactory.getLogger(PhoneAlertController.class);
    private final PhoneAlertService phoneAlertService;

    public PhoneAlertController(PhoneAlertService phoneAlertService) {
        this.phoneAlertService = phoneAlertService;
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneNumbersByFirestation(@RequestParam String firestation) {
        logger.info("Request received for phoneAlert with firestation: {}", firestation);
        List<String> phoneNumbers = phoneAlertService.getPhoneNumbersByFirestation(firestation);
        logger.info("Response: {}", phoneNumbers);
        return ResponseEntity.ok(phoneNumbers);
    }
}
