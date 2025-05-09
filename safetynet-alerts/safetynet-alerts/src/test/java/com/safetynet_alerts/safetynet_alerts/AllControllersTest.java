package com.safetynet_alerts.safetynet_alerts;

import com.safetynet_alerts.safetynet_alerts.controller.*;
import com.safetynet_alerts.safetynet_alerts.dto.*;
import com.safetynet_alerts.safetynet_alerts.model.*;
import com.safetynet_alerts.safetynet_alerts.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({
        ChildAlertController.class,
        CommunityEmailController.class,
        FireController.class,
        FirestationController.class,
        FirestationMappingController.class,
        FloodStationsController.class,
        MedicalRecordController.class,
        PersonController.class,
        PersonInfoController.class,
        PhoneAlertController.class
})
class AllControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChildAlertService childAlertService;
    @MockBean private CommunityEmailService communityEmailService;
    @MockBean private FireService fireService;
    @MockBean private FirestationService firestationService;
    @MockBean private FirestationMappingService firestationMappingService;
    @MockBean private FloodStationsService floodStationsService;
    @MockBean private MedicalRecordService medicalRecordService;
    @MockBean private PersonService personService;
    @MockBean private PersonInfoService personInfoService;
    @MockBean private PhoneAlertService phoneAlertService;

    @Test void testFireController() throws Exception {
        when(fireService.getFireByAddress("1509 Culver St")).thenReturn(new FireResponse());
        mockMvc.perform(get("/fire").param("address", "1509 Culver St")).andExpect(status().isOk());
    }

    @Test void testChildAlertController() throws Exception {
        when(childAlertService.getChildAlertByAddress("123 Main St")).thenReturn(new ChildAlertResponse());
        mockMvc.perform(get("/childAlert").param("address", "123 Main St")).andExpect(status().isOk());
    }

    @Test void testCommunityEmailController() throws Exception {
        when(communityEmailService.getEmailsByCity("Culver")).thenReturn(Set.of("test@email.com"));
        mockMvc.perform(get("/communityEmail").param("city", "Culver")).andExpect(status().isOk());
    }

    @Test void testFirestationController() throws Exception {
        when(firestationService.getPersonsByStation("1")).thenReturn(new FirestationResponse());
        mockMvc.perform(get("/firestation").param("stationNumber", "1")).andExpect(status().isOk());
    }

    @Test void testFloodStationsController() throws Exception {
        when(floodStationsService.getFloodStations(List.of("1", "2"))).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/flood/stations").param("stations", "1", "2")).andExpect(status().isOk());
    }

    @Test void testPersonInfoController() throws Exception {
        when(personInfoService.getPersonInfoByLastName("Doe")).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/personInfolastName").param("lastName", "Doe")).andExpect(status().isOk());
    }

    @Test void testPhoneAlertController() throws Exception {
        when(phoneAlertService.getPhoneNumbersByFirestation("1")).thenReturn(List.of("123-456"));
        mockMvc.perform(get("/phoneAlert").param("firestation", "1")).andExpect(status().isOk());
    }

    @Test void testAddPerson() throws Exception {
        when(personService.addPerson(new Person())).thenReturn(new Person());
        mockMvc.perform(post("/person").contentType("application/json").content("{}")).andExpect(status().isOk());
    }

    @Test void testAddFirestationMapping() throws Exception {
        when(firestationMappingService.addFirestationMapping(new Firestation())).thenReturn(new Firestation());
        mockMvc.perform(post("/firestation").contentType("application/json").content("{}")).andExpect(status().isOk());
    }

    @Test void testAddMedicalRecord() throws Exception {
        when(medicalRecordService.addMedicalRecord(new MedicalRecord())).thenReturn(new MedicalRecord());
        mockMvc.perform(post("/medicalRecord").contentType("application/json").content("{}")).andExpect(status().isOk());
    }
}