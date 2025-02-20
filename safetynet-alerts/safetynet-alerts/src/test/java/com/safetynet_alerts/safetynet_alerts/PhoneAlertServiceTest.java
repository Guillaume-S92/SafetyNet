package com.safetynet_alerts.safetynet_alerts;

import com.safetynet_alerts.safetynet_alerts.service.PhoneAlertService;
import com.safetynet_alerts.safetynet_alerts.model.Firestation;
import com.safetynet_alerts.safetynet_alerts.model.Person;
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
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhoneAlertServiceTest {

    @Mock
    private DataLoader dataLoader;

    @InjectMocks
    private PhoneAlertService phoneAlertService;

    @BeforeEach
    void setUp() {
        when(dataLoader.getFirestations()).thenReturn(Arrays.asList(
                new Firestation("123 Main St", "1"),
                new Firestation("456 Elm St", "1"),
                new Firestation("789 Oak St", "2")
        ));

        when(dataLoader.getPersons()).thenReturn(Arrays.asList(
                new Person("John", "Doe", "123 Main St", "Culver", "97451", "841-874-6512", "john.doe@email.com"),
                new Person("Jane", "Doe", "456 Elm St", "Culver", "97451", "841-874-6513", "jane.doe@email.com"),
                new Person("Mike", "Smith", "789 Oak St", "Springfield", "97451", "841-874-6599", "mike.smith@email.com"),
                new Person("Alice", "Brown", "123 Main St", "Culver", "97451", "841-874-6512", "alice.brown@email.com") // Même numéro que John
        ));
    }

    @Test
    void testGetPhoneNumbersByFirestation_ExistingStation() {
        // Exécution du service
        List<String> phoneNumbers = phoneAlertService.getPhoneNumbersByFirestation("1");

        // Vérifications
        assertNotNull(phoneNumbers);
        assertEquals(2, phoneNumbers.size()); // Unicité des numéros

        // Vérifie que les numéros attendus sont bien dans la liste
        assertTrue(phoneNumbers.contains("841-874-6512"));
        assertTrue(phoneNumbers.contains("841-874-6513"));
    }

    @Test
    void testGetPhoneNumbersByFirestation_NoMatches() {
        // Exécuter avec une caserne qui n'a pas de résidents
        List<String> phoneNumbers = phoneAlertService.getPhoneNumbersByFirestation("99");

        // Vérifications
        assertNotNull(phoneNumbers);
        assertTrue(phoneNumbers.isEmpty());
    }
}
