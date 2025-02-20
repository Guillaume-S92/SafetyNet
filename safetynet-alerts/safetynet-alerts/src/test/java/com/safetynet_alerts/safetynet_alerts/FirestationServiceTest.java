package com.safetynet_alerts.safetynet_alerts;

import com.safetynet_alerts.safetynet_alerts.dto.FirestationResponse;
import com.safetynet_alerts.safetynet_alerts.model.Firestation;
import com.safetynet_alerts.safetynet_alerts.model.MedicalRecord;
import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.service.FirestationService;
import com.safetynet_alerts.safetynet_alerts.util.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FirestationServiceTest {

    private FirestationService firestationService;
    private DataLoader dataLoader;

    @BeforeEach
    void setUp() {
        dataLoader = mock(DataLoader.class);
        firestationService = new FirestationService(dataLoader);
    }

    @Test
    void testGetPersonsByStation() {
        // Mock des firestations
        List<Firestation> firestations = Arrays.asList(
                new Firestation() {{
                    setAddress("1509 Culver St");
                    setStation("3");
                }},
                new Firestation() {{
                    setAddress("112 Steppes Pl");
                    setStation("3");
                }}
        );

        // Mock des personnes
        List<Person> persons = Arrays.asList(
                new Person() {{
                    setFirstName("John");
                    setLastName("Boyd");
                    setAddress("1509 Culver St");
                    setPhone("841-874-6512");
                }},
                new Person() {{
                    setFirstName("Jacob");
                    setLastName("Boyd");
                    setAddress("1509 Culver St");
                    setPhone("841-874-6513");
                }},
                new Person() {{
                    setFirstName("Allison");
                    setLastName("Boyd");
                    setAddress("112 Steppes Pl");
                    setPhone("841-874-9888");
                }}
        );

        // Mock des dossiers médicaux
        List<MedicalRecord> medicalRecords = Arrays.asList(
                new MedicalRecord() {{
                    setFirstName("John");
                    setLastName("Boyd");
                    setBirthdate("03/06/1984"); // 40 ans
                }},
                new MedicalRecord() {{
                    setFirstName("Jacob");
                    setLastName("Boyd");
                    setBirthdate("03/06/1989"); // 35 ans
                }},
                new MedicalRecord() {{
                    setFirstName("Allison");
                    setLastName("Boyd");
                    setBirthdate("03/06/2017"); // 7 ans
                }}
        );

        // Configuration des mocks
        when(dataLoader.getFirestations()).thenReturn(firestations);
        when(dataLoader.getPersons()).thenReturn(persons);
        when(dataLoader.getMedicalRecords()).thenReturn(medicalRecords);

        // Appel du service
        FirestationResponse response = firestationService.getPersonsByStation("3");

        // Vérifications
        assertNotNull(response);
        assertEquals(3, response.getResidents().size());
        assertEquals(2, response.getNumberOfAdults()); // John (40 ans), Jacob (35 ans)
        assertEquals(1, response.getNumberOfChildren()); // Allison (7 ans)
    }
}
