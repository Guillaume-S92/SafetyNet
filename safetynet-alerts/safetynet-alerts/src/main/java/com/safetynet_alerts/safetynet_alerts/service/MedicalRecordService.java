package com.safetynet_alerts.safetynet_alerts.service;

import com.safetynet_alerts.safetynet_alerts.model.MedicalRecord;
import com.safetynet_alerts.safetynet_alerts.util.DataLoader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {

    private final DataLoader dataLoader;

    public MedicalRecordService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        List<MedicalRecord> medicalRecords = dataLoader.getMedicalRecords();
        medicalRecords.add(medicalRecord);
        return medicalRecord;
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord updatedRecord) {
        List<MedicalRecord> medicalRecords = dataLoader.getMedicalRecords();
        Optional<MedicalRecord> existingRecord = medicalRecords.stream()
                .filter(record -> record.getFirstName().equalsIgnoreCase(updatedRecord.getFirstName())
                        && record.getLastName().equalsIgnoreCase(updatedRecord.getLastName()))
                .findFirst();

        if (existingRecord.isPresent()) {
            MedicalRecord record = existingRecord.get();
            record.setBirthdate(updatedRecord.getBirthdate());
            record.setMedications(updatedRecord.getMedications());
            record.setAllergies(updatedRecord.getAllergies());
            return record;
        } else {
            throw new RuntimeException("Medical record not found.");
        }
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) {
        List<MedicalRecord> medicalRecords = dataLoader.getMedicalRecords();
        return medicalRecords.removeIf(record -> record.getFirstName().equalsIgnoreCase(firstName)
                && record.getLastName().equalsIgnoreCase(lastName));
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return dataLoader.getMedicalRecords();
    }

}
