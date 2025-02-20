package com.safetynet_alerts.safetynet_alerts;

import com.safetynet_alerts.safetynet_alerts.controller.FirestationController;
import com.safetynet_alerts.safetynet_alerts.dto.FirestationResponse;
import com.safetynet_alerts.safetynet_alerts.service.FirestationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FirestationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FirestationService firestationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        FirestationController firestationController = new FirestationController(firestationService);
        mockMvc = MockMvcBuilders.standaloneSetup(firestationController).build();
    }

    @Test
    void testGetPersonsByStation() throws Exception {
        when(firestationService.getPersonsByStation("3")).thenReturn(new FirestationResponse());

        mockMvc.perform(get("/firestation?stationNumber=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(firestationService, times(1)).getPersonsByStation("3");
    }
}
