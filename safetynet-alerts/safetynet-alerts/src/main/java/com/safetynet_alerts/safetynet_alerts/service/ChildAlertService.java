package com.safetynet_alerts.safetynet_alerts.service;

import com.safetynet_alerts.safetynet_alerts.dto.ChildAlertResponse;
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
public class ChildAlertService {

    private final DataLoader dataLoader;

    public ChildAlertService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public ChildAlertResponse getChildAlertByAddress(String address) {
        // Récupérer toutes les personnes à cette adresse
        List<Person> personsAtAddress = dataLoader.getPersons().stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());

        // Séparer les enfants et les autres membres
        List<ChildAlertResponse.Child> children = personsAtAddress.stream()
                .filter(person -> {
                    MedicalRecord record = findMedicalRecord(person.getFirstName(), person.getLastName());
                    return record != null && calculateAge(record.getBirthdate()) <= 18;
                })
                .map(person -> {
                    MedicalRecord record = findMedicalRecord(person.getFirstName(), person.getLastName());
                    ChildAlertResponse.Child child = new ChildAlertResponse.Child();
                    child.setFirstName(person.getFirstName());
                    child.setLastName(person.getLastName());
                    child.setAge(record != null ? calculateAge(record.getBirthdate()) : 0);
                    return child;
                })
                .collect(Collectors.toList());

        List<ChildAlertResponse.OtherMember> otherMembers = personsAtAddress.stream()
                .filter(person -> {
                    MedicalRecord record = findMedicalRecord(person.getFirstName(), person.getLastName());
                    return record == null || calculateAge(record.getBirthdate()) > 18;
                })
                .map(person -> {
                    ChildAlertResponse.OtherMember other = new ChildAlertResponse.OtherMember();
                    other.setFirstName(person.getFirstName());
                    other.setLastName(person.getLastName());
                    return other;
                })
                .collect(Collectors.toList());

        // Construire la réponse
        ChildAlertResponse response = new ChildAlertResponse();
        response.setChildren(children);
        response.setOtherMembers(otherMembers);

        return response;
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
