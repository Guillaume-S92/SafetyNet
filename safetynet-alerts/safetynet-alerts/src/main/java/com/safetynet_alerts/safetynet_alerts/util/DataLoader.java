package com.safetynet_alerts.safetynet_alerts.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.model.Firestation;
import com.safetynet_alerts.safetynet_alerts.model.MedicalRecord;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader {
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalRecords;

    @PostConstruct
    public void loadData() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.json");
        if (inputStream == null) {
            System.err.println("Le fichier data.json n'a pas été trouvé dans le classpath.");
            throw new FileNotFoundException("data.json not found in resources");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Data data = objectMapper.readValue(inputStream, Data.class);
        this.persons = data.getPersons();
        this.firestations = data.getFirestations();
        this.medicalRecords = data.getMedicalRecords();
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    // Inner static class
    private static class Data {
        @JsonProperty("persons")
        private List<Person> persons;

        @JsonProperty("firestations")
        private List<Firestation> firestations;

        @JsonProperty("medicalrecords")
        private List<MedicalRecord> medicalRecords;

        // Getters and Setters
        public List<Person> getPersons() {
            return persons;
        }

        public void setPersons(List<Person> persons) {
            this.persons = persons;
        }

        public List<Firestation> getFirestations() {
            return firestations;
        }

        public void setFirestations(List<Firestation> firestations) {
            this.firestations = firestations;
        }

        public List<MedicalRecord> getMedicalRecords() {
            return medicalRecords;
        }

        public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
            this.medicalRecords = medicalRecords;
        }
    }

}
