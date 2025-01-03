package com.safetynet_alerts.safetynet_alerts.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.model.Firestation;
import com.safetynet_alerts.safetynet_alerts.model.MedicalRecord;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.stereotype.Component;


import java.io.File;
import java.util.List;

@Component
@Data
public class DataLoader {
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalRecords;

    @PostConstruct
    public void loadData() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/data.json");
        Data data = objectMapper.readValue(file, Data.class);
        this.persons = data.getPersons();
        this.firestations = data.getFirestations();
        this.medicalRecords = data.getMedicalRecords();
    }

    @lombok.Data
    private static class Data {
        private List<Person> persons;
        private List<Firestation> firestations;
        private List<MedicalRecord> medicalRecords;
    }
}
