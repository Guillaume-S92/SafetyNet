package com.safetynet_alerts.safetynet_alerts.controller;

import com.safetynet_alerts.safetynet_alerts.service.CommunityEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class CommunityEmailController {

    private static final Logger logger = LoggerFactory.getLogger(CommunityEmailController.class);
    private final CommunityEmailService communityEmailService;

    public CommunityEmailController(CommunityEmailService communityEmailService) {
        this.communityEmailService = communityEmailService;
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<Set<String>> getCommunityEmails(@RequestParam String city) {
        logger.info("Request received for community emails in city: {}", city);
        Set<String> emails = communityEmailService.getEmailsByCity(city);
        logger.info("Response: {}", emails);
        return ResponseEntity.ok(emails);
    }
}
