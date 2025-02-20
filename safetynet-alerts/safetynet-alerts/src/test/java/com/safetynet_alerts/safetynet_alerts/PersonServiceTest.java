package com.safetynet_alerts.safetynet_alerts;

import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.service.PersonService;
import com.safetynet_alerts.safetynet_alerts.util.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private DataLoader dataLoader;

    @InjectMocks
    private PersonService personService;

    private List<Person> personList;

    @BeforeEach
    void setUp() {
        personList = new ArrayList<>();
        when(dataLoader.getPersons()).thenReturn(personList);
    }

    @Test
    void testAddPerson() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("123 Main St");

        Person addedPerson = personService.addPerson(person);
        assertNotNull(addedPerson);
        assertEquals("John", addedPerson.getFirstName());
        assertEquals("Doe", addedPerson.getLastName());
        assertEquals(1, personList.size());
    }

    @Test
    void testUpdatePerson() {
        Person person = new Person();
        person.setFirstName("Jane");
        person.setLastName("Doe");
        person.setAddress("456 Elm St");
        personList.add(person);

        Person updatedPerson = new Person();
        updatedPerson.setFirstName("Jane");
        updatedPerson.setLastName("Doe");
        updatedPerson.setAddress("789 Oak St");
        updatedPerson.setPhone("555-1234");

        Person result = personService.updatePerson(updatedPerson);
        assertNotNull(result);
        assertEquals("789 Oak St", result.getAddress());
        assertEquals("555-1234", result.getPhone());
    }

    @Test
    void testUpdatePersonNotFound() {
        Person updatedPerson = new Person();
        updatedPerson.setFirstName("Jack");
        updatedPerson.setLastName("Smith");

        Exception exception = assertThrows(RuntimeException.class, () ->
                personService.updatePerson(updatedPerson));

        assertEquals("Person not found.", exception.getMessage());
    }

    @Test
    void testDeletePerson() {
        Person person = new Person();
        person.setFirstName("Alice");
        person.setLastName("Johnson");
        personList.add(person);

        boolean deleted = personService.deletePerson("Alice", "Johnson");
        assertTrue(deleted);
        assertEquals(0, personList.size());
    }

    @Test
    void testDeletePersonNotFound() {
        boolean deleted = personService.deletePerson("Bob", "Brown");
        assertFalse(deleted);
    }
}
