package com.safetynet_alerts.safetynet_alerts;

import com.safetynet_alerts.safetynet_alerts.model.MedicalRecord;
import com.safetynet_alerts.safetynet_alerts.service.MedicalRecordService;
import com.safetynet_alerts.safetynet_alerts.util.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {

    @Mock
    private DataLoader dataLoader;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    private List<MedicalRecord> medicalRecords;

    @BeforeEach
    void setUp() {
        medicalRecords = new ArrayList<>();
        when(dataLoader.getMedicalRecords()).thenReturn(medicalRecords);
    }

    @Test
    void testAddMedicalRecord() {
        MedicalRecord newRecord = new MedicalRecord();
        newRecord.setFirstName("John");
        newRecord.setLastName("Doe");
        newRecord.setBirthdate("01/01/2000");
        newRecord.setMedications(List.of("aspirin:100mg"));
        newRecord.setAllergies(List.of("pollen"));

        MedicalRecord addedRecord = medicalRecordService.addMedicalRecord(newRecord);

        assertEquals(newRecord, addedRecord);
        assertEquals(1, medicalRecords.size());
    }

    @Test
    void testUpdateMedicalRecord() {
        MedicalRecord existingRecord = new MedicalRecord();
        existingRecord.setFirstName("John");
        existingRecord.setLastName("Doe");
        existingRecord.setBirthdate("01/01/1990");
        medicalRecords.add(existingRecord);

        MedicalRecord updatedRecord = new MedicalRecord();
        updatedRecord.setFirstName("John");
        updatedRecord.setLastName("Doe");
        updatedRecord.setBirthdate("01/01/2000");
        updatedRecord.setMedications(List.of("ibuprofen:200mg"));
        updatedRecord.setAllergies(List.of("dust"));

        MedicalRecord result = medicalRecordService.updateMedicalRecord(updatedRecord);

        assertEquals("01/01/2000", result.getBirthdate());
        assertEquals(List.of("ibuprofen:200mg"), result.getMedications());
        assertEquals(List.of("dust"), result.getAllergies());
    }

    @Test
    void testDeleteMedicalRecord() {
        MedicalRecord record = new MedicalRecord();
        record.setFirstName("John");
        record.setLastName("Doe");
        medicalRecords.add(record);

        boolean deleted = medicalRecordService.deleteMedicalRecord("John", "Doe");

        assertTrue(deleted);
        assertTrue(medicalRecords.isEmpty());
    }

    @Test
    void testDeleteMedicalRecord_NotFound() {
        boolean deleted = medicalRecordService.deleteMedicalRecord("Jane", "Doe");

        assertFalse(deleted);
    }

    @Test
    void testGetAllMedicalRecords() {
        MedicalRecord record1 = new MedicalRecord();
        record1.setFirstName("John");
        record1.setLastName("Doe");

        MedicalRecord record2 = new MedicalRecord();
        record2.setFirstName("Jane");
        record2.setLastName("Doe");

        medicalRecords.add(record1);
        medicalRecords.add(record2);

        List<MedicalRecord> result = medicalRecordService.getAllMedicalRecords();

        assertEquals(2, result.size());
    }
}
