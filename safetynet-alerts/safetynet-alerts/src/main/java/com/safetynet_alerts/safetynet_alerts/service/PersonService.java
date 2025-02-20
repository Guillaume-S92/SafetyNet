package com.safetynet_alerts.safetynet_alerts.service;

import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.util.DataLoader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final DataLoader dataLoader;

    public PersonService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public Person addPerson(Person person) {
        List<Person> persons = dataLoader.getPersons();
        persons.add(person);
        return person;
    }

    public Person updatePerson(Person updatedPerson) {
        List<Person> persons = dataLoader.getPersons();
        Optional<Person> existingPerson = persons.stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase(updatedPerson.getFirstName())
                        && person.getLastName().equalsIgnoreCase(updatedPerson.getLastName()))
                .findFirst();

        if (existingPerson.isPresent()) {
            Person person = existingPerson.get();
            person.setAddress(updatedPerson.getAddress());
            person.setCity(updatedPerson.getCity());
            person.setZip(updatedPerson.getZip());
            person.setPhone(updatedPerson.getPhone());
            person.setEmail(updatedPerson.getEmail());
            return person;
        } else {
            throw new RuntimeException("Person not found.");
        }
    }

    public boolean deletePerson(String firstName, String lastName) {
        List<Person> persons = dataLoader.getPersons();
        return persons.removeIf(person -> person.getFirstName().equalsIgnoreCase(firstName)
                && person.getLastName().equalsIgnoreCase(lastName));
    }
}
