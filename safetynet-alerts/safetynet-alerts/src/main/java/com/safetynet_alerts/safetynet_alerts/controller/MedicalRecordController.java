package com.safetynet_alerts.safetynet_alerts.controller;

import com.safetynet_alerts.safetynet_alerts.model.MedicalRecord;
import com.safetynet_alerts.safetynet_alerts.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        logger.info("Request to add medical record: {}", medicalRecord);
        MedicalRecord createdRecord = medicalRecordService.addMedicalRecord(medicalRecord);
        return ResponseEntity.ok(createdRecord);
    }

    @PutMapping
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        logger.info("Request to update medical record: {}", medicalRecord);
        MedicalRecord updatedRecord = medicalRecordService.updateMedicalRecord(medicalRecord);
        return ResponseEntity.ok(updatedRecord);
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
        logger.info("Request to get all medical records");
        List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
        return ResponseEntity.ok(medicalRecords);
    }


    @DeleteMapping
    public ResponseEntity<String> deleteMedicalRecord(
            @RequestParam String firstName, @RequestParam String lastName) {
        logger.info("Request to delete medical record for: {} {}", firstName, lastName);
        boolean isDeleted = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        if (isDeleted) {
            return ResponseEntity.ok("Medical record deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
