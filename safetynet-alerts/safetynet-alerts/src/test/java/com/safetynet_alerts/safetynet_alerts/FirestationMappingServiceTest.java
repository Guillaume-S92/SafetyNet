package com.safetynet_alerts.safetynet_alerts;

import com.safetynet_alerts.safetynet_alerts.model.Firestation;
import com.safetynet_alerts.safetynet_alerts.service.FirestationMappingService;
import com.safetynet_alerts.safetynet_alerts.util.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FirestationMappingServiceTest {

    @Mock
    private DataLoader dataLoader;

    @InjectMocks
    private FirestationMappingService firestationMappingService;

    private List<Firestation> firestations;

    @BeforeEach
    void setUp() {
        firestations = new ArrayList<>();
        firestations.add(new Firestation("123 Main St", "1"));
        firestations.add(new Firestation("456 Elm St", "2"));

        lenient().when(dataLoader.getFirestations()).thenReturn(firestations);
    }

    @Test
    void testAddFirestationMapping() {
        Firestation newMapping = new Firestation("789 Oak St", "3");

        Firestation addedMapping = firestationMappingService.addFirestationMapping(newMapping);

        assertEquals(newMapping, addedMapping);
        assertEquals(3, firestations.size());
        assertTrue(firestations.contains(newMapping));
    }

    @Test
    void testUpdateFirestationMapping_ExistingAddress() {
        Firestation updatedMapping = new Firestation("123 Main St", "3");

        Firestation result = firestationMappingService.updateFirestationMapping(updatedMapping);

        assertNotNull(result);
        assertEquals("3", result.getStation());
    }

    @Test
    void testUpdateFirestationMapping_NonExistingAddress() {
        Firestation updatedMapping = new Firestation("Nonexistent St", "5");

        Exception exception = assertThrows(RuntimeException.class, () ->
                firestationMappingService.updateFirestationMapping(updatedMapping)
        );

        assertEquals("Firestation mapping not found.", exception.getMessage());
    }

    @Test
    void testDeleteFirestationMapping_ExistingAddress() {
        boolean deleted = firestationMappingService.deleteFirestationMapping("123 Main St");

        assertTrue(deleted);
        assertEquals(1, firestations.size());
    }

    @Test
    void testDeleteFirestationMapping_NonExistingAddress() {
        boolean deleted = firestationMappingService.deleteFirestationMapping("Nonexistent St");

        assertFalse(deleted);
        assertEquals(2, firestations.size());
    }
}
