package com.safetynet_alerts.safetynet_alerts;

import com.safetynet_alerts.safetynet_alerts.dto.FloodStationsResponse;
import com.safetynet_alerts.safetynet_alerts.model.Firestation;
import com.safetynet_alerts.safetynet_alerts.model.MedicalRecord;
import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.service.FloodStationsService;
import com.safetynet_alerts.safetynet_alerts.util.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FloodStationsServiceTest {

    private FloodStationsService floodStationsService;
    private DataLoader dataLoader;

    @BeforeEach
    void setUp() {
        dataLoader = mock(DataLoader.class);
        floodStationsService = new FloodStationsService(dataLoader);
    }

    @Test
    void testGetFloodStations() {
        // Mock des firestations
        List<Firestation> firestations = Arrays.asList(
                new Firestation() {{
                    setAddress("1509 Culver St");
                    setStation("3");
                }},
                new Firestation() {{
                    setAddress("112 Steppes Pl");
                    setStation("3");
                }},
                new Firestation() {{
                    setAddress("947 E. Rose Dr");
                    setStation("1");
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
                    setFirstName("Brian");
                    setLastName("Stelzer");
                    setAddress("947 E. Rose Dr");
                    setPhone("841-874-7784");
                }}
        );

        // Mock des dossiers médicaux
        List<MedicalRecord> medicalRecords = Arrays.asList(
                new MedicalRecord() {{
                    setFirstName("John");
                    setLastName("Boyd");
                    setBirthdate("03/06/1984"); // 40 ans
                    setMedications(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));
                    setAllergies(Arrays.asList("nillacilan"));
                }},
                new MedicalRecord() {{
                    setFirstName("Jacob");
                    setLastName("Boyd");
                    setBirthdate("03/06/1989"); // 35 ans
                    setMedications(Arrays.asList("pharmacol:5000mg"));
                    setAllergies(Arrays.asList());
                }},
                new MedicalRecord() {{
                    setFirstName("Brian");
                    setLastName("Stelzer");
                    setBirthdate("12/06/1975"); // 49 ans
                    setMedications(Arrays.asList("ibupurin:200mg"));
                    setAllergies(Arrays.asList("nillacilan"));
                }}
        );

        // Configuration des mocks
        when(dataLoader.getFirestations()).thenReturn(firestations);
        when(dataLoader.getPersons()).thenReturn(persons);
        when(dataLoader.getMedicalRecords()).thenReturn(medicalRecords);

        // Appel du service avec les stations "3" et "1"
        List<FloodStationsResponse> response = floodStationsService.getFloodStations(Arrays.asList("3", "1"));

        // Vérifications
        assertNotNull(response);
        assertEquals(2, response.size());

        // Vérifier le premier foyer (1509 Culver St)
        FloodStationsResponse firstHousehold = response.stream()
                .filter(r -> r.getAddress().equals("1509 Culver St"))
                .findFirst().orElse(null);
        assertNotNull(firstHousehold);
        assertEquals(2, firstHousehold.getResidents().size());

        // Vérifier les infos du premier résident
        FloodStationsResponse.ResidentInfo johnInfo = firstHousehold.getResidents().stream()
                .filter(r -> r.getFirstName().equals("John"))
                .findFirst().orElse(null);
        assertNotNull(johnInfo);
        assertEquals("841-874-6512", johnInfo.getPhone());
        assertEquals(40, johnInfo.getAge());
        assertEquals(2, johnInfo.getMedications().size());
        assertEquals(1, johnInfo.getAllergies().size());

        // Vérifier le deuxième foyer (947 E. Rose Dr)
        FloodStationsResponse secondHousehold = response.stream()
                .filter(r -> r.getAddress().equals("947 E. Rose Dr"))
                .findFirst().orElse(null);
        assertNotNull(secondHousehold);
        assertEquals(1, secondHousehold.getResidents().size());

        // Vérifier les infos du résident Brian
        FloodStationsResponse.ResidentInfo brianInfo = secondHousehold.getResidents().get(0);
        assertEquals("Brian", brianInfo.getFirstName());
        assertEquals(49, brianInfo.getAge());
        assertEquals(1, brianInfo.getMedications().size());
        assertEquals(1, brianInfo.getAllergies().size());
    }
}
