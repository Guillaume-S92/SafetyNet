package com.safetynet_alerts.safetynet_alerts.controller;

import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        logger.info("Request to add a new person: {}", person);
        Person createdPerson = personService.addPerson(person);
        return ResponseEntity.ok(createdPerson);
    }

    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        logger.info("Request to update person: {}", person);
        Person updatedPerson = personService.updatePerson(person);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping
    public ResponseEntity<String> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        logger.info("Request to delete person: {} {}", firstName, lastName);
        boolean isDeleted = personService.deletePerson(firstName, lastName);
        if (isDeleted) {
            return ResponseEntity.ok("Person deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
