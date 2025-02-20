package com.safetynet_alerts.safetynet_alerts.service;

import com.safetynet_alerts.safetynet_alerts.dto.PersonInfoResponse;
import com.safetynet_alerts.safetynet_alerts.model.MedicalRecord;
import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.util.DataLoader;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonInfoService {

    private final DataLoader dataLoader;

    public PersonInfoService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public List<PersonInfoResponse> getPersonInfoByLastName(String lastName) {
        // Récupérer toutes les personnes avec le nom de famille donné
        return dataLoader.getPersons().stream()
                .filter(person -> person.getLastName().equalsIgnoreCase(lastName))
                .map(person -> {
                    PersonInfoResponse info = new PersonInfoResponse();
                    info.setFirstName(person.getFirstName());
                    info.setLastName(person.getLastName());
                    info.setAddress(person.getAddress());
                    info.setEmail(person.getEmail());

                    MedicalRecord record = findMedicalRecord(person.getFirstName(), person.getLastName());
                    if (record != null) {
                        info.setAge(calculateAge(record.getBirthdate()));
                        info.setMedications(record.getMedications());
                        info.setAllergies(record.getAllergies());
                    }

                    return info;
                })
                .collect(Collectors.toList());
    }

    private MedicalRecord findMedicalRecord(String firstName, String lastName) {
        return dataLoader.getMedicalRecords().stream()
                .filter(record -> record.getFirstName().equals(firstName) && record.getLastName().equals(lastName))
                .findFirst()
                .orElse(null);
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
