package com.safetynet_alerts.safetynet_alerts;

import com.safetynet_alerts.safetynet_alerts.dto.FireResponse;
import com.safetynet_alerts.safetynet_alerts.model.Firestation;
import com.safetynet_alerts.safetynet_alerts.model.MedicalRecord;
import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.service.FireService;
import com.safetynet_alerts.safetynet_alerts.util.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireServiceTest {

    @Mock
    private DataLoader dataLoader;

    @InjectMocks
    private FireService fireService;

    private List<Person> mockPersons;
    private List<Firestation> mockFirestations;
    private List<MedicalRecord> mockMedicalRecords;

    @BeforeEach
    void setUp() {
        mockPersons = Arrays.asList(
                new Person("John", "Doe", "1509 Culver St", "Culver", "97451", "841-874-6512", "johndoe@email.com"),
                new Person("Jane", "Doe", "1509 Culver St", "Culver", "97451", "841-874-6513", "janedoe@email.com")
        );

        mockFirestations = Collections.singletonList(
                new Firestation("1509 Culver St", "3")
        );

        mockMedicalRecords = Arrays.asList(
                new MedicalRecord("John", "Doe", "01/01/1985", Arrays.asList("med1:100mg"), Arrays.asList("allergy1")),
                new MedicalRecord("Jane", "Doe", "02/02/1990", Arrays.asList("med2:200mg"), Arrays.asList("allergy2"))
        );

        when(dataLoader.getPersons()).thenReturn(mockPersons);
        when(dataLoader.getFirestations()).thenReturn(mockFirestations);
        lenient().when(dataLoader.getMedicalRecords()).thenReturn(mockMedicalRecords);
    }


    @Test
    void testGetFireByAddress() {
        // Exécution du service
        FireResponse response = fireService.getFireByAddress("1509 Culver St");

        // Vérifications
        assertNotNull(response);
        assertEquals("3", response.getStationNumber());
        assertEquals(2, response.getResidents().size());

        // Vérification des résidents
        FireResponse.ResidentInfo johnInfo = response.getResidents().get(0);
        assertEquals("John", johnInfo.getFirstName());
        assertEquals("Doe", johnInfo.getLastName());
        assertEquals("841-874-6512", johnInfo.getPhone());
        assertTrue(Math.abs(39 - johnInfo.getAge()) <= 1, "L'âge calculé est incorrect.");
        assertTrue(johnInfo.getMedications().contains("med1:100mg"));
        assertTrue(johnInfo.getAllergies().contains("allergy1"));

        FireResponse.ResidentInfo janeInfo = response.getResidents().get(1);
        assertEquals("Jane", janeInfo.getFirstName());
        assertEquals("Doe", janeInfo.getLastName());
        assertEquals("841-874-6513", janeInfo.getPhone());
        assertTrue(Math.abs(34 - janeInfo.getAge()) <= 1, "L'âge calculé pour Jane est incorrect.");
        assertTrue(janeInfo.getMedications().contains("med2:200mg"));
        assertTrue(janeInfo.getAllergies().contains("allergy2"));
    }

    @Test
    void testGetFireByAddress_NoResidents() {
        // Adresse inexistante
        FireResponse response = fireService.getFireByAddress("Nonexistent Address");

        assertNotNull(response);
        assertNull(response.getStationNumber());
        assertTrue(response.getResidents().isEmpty());

        verify(dataLoader, atLeastOnce()).getPersons();
        verify(dataLoader, atLeastOnce()).getFirestations();
        verify(dataLoader, never()).getMedicalRecords(); // ✅ La méthode ne doit pas être appelée
    }

}
