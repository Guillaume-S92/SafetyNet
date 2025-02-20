package com.safetynet_alerts.safetynet_alerts;

import com.safetynet_alerts.safetynet_alerts.model.Firestation;
import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.service.PhoneAlertService;
import com.safetynet_alerts.safetynet_alerts.util.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PhoneAlertServiceTest {

    private PhoneAlertService phoneAlertService;

    @Mock
    private DataLoader dataLoader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        phoneAlertService = new PhoneAlertService(dataLoader);
    }

    @Test
    void testGetPhoneNumbersByFirestation() {
        when(dataLoader.getFirestations()).thenReturn(List.of(
                new Firestation("1509 Culver St", "3")
        ));

        when(dataLoader.getPersons()).thenReturn(List.of(
                new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com")
        ));

        List<String> phoneNumbers = phoneAlertService.getPhoneNumbersByFirestation("3");

        assertEquals(1, phoneNumbers.size());
        assertEquals("841-874-6512", phoneNumbers.get(0));
    }
}
