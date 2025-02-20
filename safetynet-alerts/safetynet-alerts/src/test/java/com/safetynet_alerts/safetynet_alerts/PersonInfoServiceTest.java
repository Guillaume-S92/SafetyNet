package com.safetynet_alerts.safetynet_alerts;

import com.safetynet_alerts.safetynet_alerts.dto.PersonInfoResponse;
import com.safetynet_alerts.safetynet_alerts.model.MedicalRecord;
import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.service.PersonInfoService;
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
class PersonInfoServiceTest {

    @Mock
    private DataLoader dataLoader;

    @InjectMocks
    private PersonInfoService personInfoService;

    private List<Person> mockPersons;
    private List<MedicalRecord> mockMedicalRecords;

    @BeforeEach
    void setUp() {
        mockPersons = Arrays.asList(
                new Person("John", "Doe", "123 Main St", "Culver", "97451", "841-874-6512", "john.doe@email.com"),
                new Person("Jane", "Doe", "123 Main St", "Culver", "97451", "841-874-6513", "jane.doe@email.com"),
                new Person("Mike", "Smith", "456 Elm St", "Culver", "97451", "841-874-6599", "mike.smith@email.com")
        );

        mockMedicalRecords = Arrays.asList(
                new MedicalRecord("John", "Doe", "01/01/1985", Arrays.asList("med1:100mg"), Arrays.asList("allergy1")),
                new MedicalRecord("Jane", "Doe", "02/02/1990", Arrays.asList("med2:200mg"), Arrays.asList("allergy2"))
        );

        when(dataLoader.getPersons()).thenReturn(mockPersons);
        lenient().when(dataLoader.getMedicalRecords()).thenReturn(mockMedicalRecords);
    }

    @Test
    void testGetPersonInfoByLastName_ExistingLastName() {
        // Exécution du service pour récupérer les infos des "Doe"
        List<PersonInfoResponse> response = personInfoService.getPersonInfoByLastName("Doe");

        // Vérifications
        assertNotNull(response);
        assertEquals(2, response.size());

        // Vérifier les infos de John Doe
        PersonInfoResponse johnInfo = response.get(0);
        assertEquals("John", johnInfo.getFirstName());
        assertEquals("Doe", johnInfo.getLastName());
        assertEquals("123 Main St", johnInfo.getAddress());
        assertEquals("john.doe@email.com", johnInfo.getEmail());
        assertTrue(Math.abs(39 - johnInfo.getAge()) <= 1, "L'âge de John est incorrect.");
        assertTrue(johnInfo.getMedications().contains("med1:100mg"));
        assertTrue(johnInfo.getAllergies().contains("allergy1"));

        // Vérifier les infos de Jane Doe
        PersonInfoResponse janeInfo = response.get(1);
        assertEquals("Jane", janeInfo.getFirstName());
        assertEquals("Doe", janeInfo.getLastName());
        assertEquals("123 Main St", janeInfo.getAddress());
        assertEquals("jane.doe@email.com", janeInfo.getEmail());
        assertTrue(Math.abs(34 - janeInfo.getAge()) <= 1, "L'âge de Jane est incorrect.");
        assertTrue(janeInfo.getMedications().contains("med2:200mg"));
        assertTrue(janeInfo.getAllergies().contains("allergy2"));
    }

    @Test
    void testGetPersonInfoByLastName_NoMatches() {
        // Rechercher un nom qui n'existe pas
        List<PersonInfoResponse> response = personInfoService.getPersonInfoByLastName("Nonexistent");

        // Vérifications
        assertNotNull(response);
        assertTrue(response.isEmpty());
    }
}
