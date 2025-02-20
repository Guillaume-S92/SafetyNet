package com.safetynet_alerts.safetynet_alerts;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FirestationIntegrationTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void testGetPersonsByStation() {
        String url = "http://localhost:" + port + "/firestation?stationNumber=3";
        String response = restTemplate.getForObject(url, String.class);

        assertNotNull(response);
        assertTrue(response.contains("residents"));
    }
}
