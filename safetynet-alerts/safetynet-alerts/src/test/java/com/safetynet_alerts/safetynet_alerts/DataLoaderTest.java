package com.safetynet_alerts.safetynet_alerts;

import com.safetynet_alerts.safetynet_alerts.model.Firestation;
import com.safetynet_alerts.safetynet_alerts.model.MedicalRecord;
import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.util.DataLoader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataLoaderTest {

    @Test
    void testLoadDataFromJson() throws Exception {
        DataLoader dataLoader = new DataLoader();

        // Forcer l'appel de loadData()
        dataLoader.loadData();

        List<Person> persons = dataLoader.getPersons();
        List<Firestation> firestations = dataLoader.getFirestations();
        List<MedicalRecord> medicalRecords = dataLoader.getMedicalRecords();

        assertNotNull(persons, "La liste des personnes ne doit pas être nulle");
        assertFalse(persons.isEmpty(), "La liste des personnes ne doit pas être vide");

        assertNotNull(firestations, "La liste des firestations ne doit pas être nulle");
        assertFalse(firestations.isEmpty(), "La liste des firestations ne doit pas être vide");

        assertNotNull(medicalRecords, "La liste des dossiers médicaux ne doit pas être nulle");
        assertFalse(medicalRecords.isEmpty(), "La liste des dossiers médicaux ne doit pas être vide");
    }
}