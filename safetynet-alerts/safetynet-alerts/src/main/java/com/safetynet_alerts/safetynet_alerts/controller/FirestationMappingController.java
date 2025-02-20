package com.safetynet_alerts.safetynet_alerts.controller;

import com.safetynet_alerts.safetynet_alerts.model.Firestation;
import com.safetynet_alerts.safetynet_alerts.service.FirestationMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firestation")
public class FirestationMappingController {

    private static final Logger logger = LoggerFactory.getLogger(FirestationMappingController.class);
    private final FirestationMappingService firestationMappingService;

    public FirestationMappingController(FirestationMappingService firestationMappingService) {
        this.firestationMappingService = firestationMappingService;
    }

    @PostMapping
    public ResponseEntity<Firestation> addMapping(@RequestBody Firestation firestation) {
        logger.info("Request to add firestation mapping: {}", firestation);
        Firestation createdMapping = firestationMappingService.addFirestationMapping(firestation);
        return ResponseEntity.ok(createdMapping);
    }

    @PutMapping
    public ResponseEntity<Firestation> updateMapping(@RequestBody Firestation firestation) {
        logger.info("Request to update firestation mapping: {}", firestation);
        Firestation updatedMapping = firestationMappingService.updateFirestationMapping(firestation);
        return ResponseEntity.ok(updatedMapping);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMapping(@RequestParam String address) {
        logger.info("Request to delete firestation mapping for address: {}", address);
        boolean isDeleted = firestationMappingService.deleteFirestationMapping(address);
        if (isDeleted) {
            return ResponseEntity.ok("Mapping deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
