package com.safetynet_alerts.safetynet_alerts;

import com.safetynet_alerts.safetynet_alerts.dto.FirestationResponse;
import com.safetynet_alerts.safetynet_alerts.model.Firestation;
import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.service.FirestationService;
import com.safetynet_alerts.safetynet_alerts.util.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FirestationServiceTest {

    private FirestationService firestationService;

    @Mock
    private DataLoader dataLoader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        firestationService = new FirestationService(dataLoader);
    }

    @Test
    void testGetPersonsByStation() {
        when(dataLoader.getFirestations()).thenReturn(List.of(
                new Firestation("1509 Culver St", "3"),
                new Firestation("834 Binoc Ave", "3")
        ));

        when(dataLoader.getPersons()).thenReturn(List.of(
                new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
                new Person("Tessa", "Carman", "834 Binoc Ave", "Culver", "97451", "841-874-6512", "tenz@email.com")
        ));

        FirestationResponse response = firestationService.getPersonsByStation("3");

        assertNotNull(response);
        assertEquals(2, response.getResidents().size());
        assertEquals("John", response.getResidents().get(0).getFirstName());
    }
}
