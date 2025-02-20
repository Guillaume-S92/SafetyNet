package com.safetynet_alerts.safetynet_alerts.controller;

import com.safetynet_alerts.safetynet_alerts.dto.PersonInfoResponse;
import com.safetynet_alerts.safetynet_alerts.service.PersonInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonInfoController {

    private static final Logger logger = LoggerFactory.getLogger(PersonInfoController.class);
    private final PersonInfoService personInfoService;

    public PersonInfoController(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }

    @GetMapping("/personInfolastName")
    public ResponseEntity<List<PersonInfoResponse>> getPersonInfo(@RequestParam String lastName) {
        logger.info("Request received for personInfo with lastName: {}", lastName);
        List<PersonInfoResponse> response = personInfoService.getPersonInfoByLastName(lastName);
        logger.info("Response: {}", response);
        return ResponseEntity.ok(response);
    }
}
