package com.safetynet_alerts.safetynet_alerts;

import com.safetynet_alerts.safetynet_alerts.dto.ChildAlertResponse;
import com.safetynet_alerts.safetynet_alerts.model.MedicalRecord;
import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.service.ChildAlertService;
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
class ChildAlertServiceTest {

    @Mock
    private DataLoader dataLoader;

    @InjectMocks
    private ChildAlertService childAlertService;

    @BeforeEach
    void setUp() {
        // Mock des personnes habitant à "123 Main St"
        List<Person> persons = Arrays.asList(
                new Person("John", "Doe", "123 Main St", "Culver", "97451", "841-874-6512", "john.doe@email.com"),
                new Person("Jane", "Doe", "123 Main St", "Culver", "97451", "841-874-6513", "jane.doe@email.com"),
                new Person("Mike", "Doe", "123 Main St", "Culver", "97451", "841-874-6599", "mike.doe@email.com") // Enfant
        );

        // Mock des dossiers médicaux
        List<MedicalRecord> medicalRecords = Arrays.asList(
                new MedicalRecord("John", "Doe", "01/01/1980", List.of("med1:100mg"), List.of("allergy1")), // Adulte
                new MedicalRecord("Jane", "Doe", "02/02/1982", List.of("med2:200mg"), List.of("allergy2")), // Adulte
                new MedicalRecord("Mike", "Doe", "01/01/2015", List.of("vitamins:50mg"), List.of("pollen"))  // Enfant (8 ans)
        );

        lenient().when(dataLoader.getPersons()).thenReturn(persons);
        lenient().when(dataLoader.getMedicalRecords()).thenReturn(medicalRecords);
    }


    @Test
    void testGetChildAlertByAddress_WithChildren() {
        // Exécution du service
        ChildAlertResponse response = childAlertService.getChildAlertByAddress("123 Main St");

        // Vérifications
        assertNotNull(response);
        assertEquals(1, response.getChildren().size());
        assertEquals(2, response.getOtherMembers().size());

        // Vérifier l'enfant Mike
        ChildAlertResponse.Child child = response.getChildren().get(0);
        assertEquals("Mike", child.getFirstName());
        assertEquals("Doe", child.getLastName());
        assertTrue(Math.abs(9 - child.getAge()) <= 1, "L'âge de Mike est incorrect.");

        // Vérifier les adultes
        List<String> adultNames = response.getOtherMembers().stream()
                .map(ChildAlertResponse.OtherMember::getFirstName)
                .toList();
        assertTrue(adultNames.contains("John"));
        assertTrue(adultNames.contains("Jane"));
    }

    @Test
    void testGetChildAlertByAddress_NoChildren() {
        // Mock d'une adresse sans enfants
        when(dataLoader.getPersons()).thenReturn(Collections.singletonList(
                new Person("Alice", "Smith", "456 Elm St", "Culver", "97451", "841-874-7777", "alice.smith@email.com")
        ));
        when(dataLoader.getMedicalRecords()).thenReturn(Collections.singletonList(
                new MedicalRecord("Alice", "Smith", "01/01/1980", List.of(), List.of())
        ));

        // Exécution du service
        ChildAlertResponse response = childAlertService.getChildAlertByAddress("456 Elm St");

        // Vérifications
        assertNotNull(response);
        assertTrue(response.getChildren().isEmpty());
        assertEquals(1, response.getOtherMembers().size());
        assertEquals("Alice", response.getOtherMembers().get(0).getFirstName());
    }

    @Test
    void testGetChildAlertByAddress_NonexistentAddress() {
        // Exécution du service avec une adresse qui n'existe pas
        ChildAlertResponse response = childAlertService.getChildAlertByAddress("Nonexistent Address");

        // Vérifications
        assertNotNull(response);
        assertTrue(response.getChildren().isEmpty());
        assertTrue(response.getOtherMembers().isEmpty());
    }
}
