package com.safetynet_alerts.safetynet_alerts;

import com.safetynet_alerts.safetynet_alerts.service.CommunityEmailService;
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
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommunityEmailServiceTest {

    @Mock
    private DataLoader dataLoader;

    @InjectMocks
    private CommunityEmailService communityEmailService;

    @BeforeEach
    void setUp() {
        when(dataLoader.getPersons()).thenReturn(Arrays.asList(
                new Person("John", "Doe", "123 Main St", "Culver", "97451", "841-874-6512", "john.doe@email.com"),
                new Person("Jane", "Doe", "456 Elm St", "Culver", "97451", "841-874-6513", "jane.doe@email.com"),
                new Person("Mike", "Smith", "789 Oak St", "Springfield", "97451", "841-874-6599", "mike.smith@email.com"),
                new Person("Alice", "Brown", "101 Pine St", "Culver", "97451", "841-874-6588", "alice.brown@email.com"),
                new Person("John", "Doe", "123 Main St", "Culver", "97451", "841-874-6512", "john.doe@email.com") // Email dupliqué
        ));
    }

    @Test
    void testGetEmailsByCity_ExistingCity() {
        // Exécution du service
        Set<String> emails = communityEmailService.getEmailsByCity("Culver");

        // Vérifications
        assertNotNull(emails);
        assertEquals(3, emails.size()); // Vérifie qu'il n'y a pas de doublons

        // Vérifie que les emails attendus sont bien dans la liste
        assertTrue(emails.contains("john.doe@email.com"));
        assertTrue(emails.contains("jane.doe@email.com"));
        assertTrue(emails.contains("alice.brown@email.com"));
    }

    @Test
    void testGetEmailsByCity_NoMatches() {
        // Exécuter avec une ville qui n'existe pas
        Set<String> emails = communityEmailService.getEmailsByCity("Unknown City");

        // Vérifications
        assertNotNull(emails);
        assertTrue(emails.isEmpty());
    }
}
