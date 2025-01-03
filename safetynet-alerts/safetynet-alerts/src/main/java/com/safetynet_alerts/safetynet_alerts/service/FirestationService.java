package com.safetynet_alerts.safetynet_alerts.service;

import com.safetynet_alerts.safetynet_alerts.dto.FirestationResponse;
import com.safetynet_alerts.safetynet_alerts.model.Firestation;
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
public class FirestationService {
    private final DataLoader dataLoader;

    public FirestationService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public FirestationResponse getPersonsByStation(String stationNumber) {
        List<String> addresses = dataLoader.getFirestations().stream()
                .filter(f -> f.getStation().equals(stationNumber))
                .map(Firestation::getAddress)
                .collect(Collectors.toList());

        List<Person> residents = dataLoader.getPersons().stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .collect(Collectors.toList());

        List<FirestationResponse.Resident> residentDtos = residents.stream()
                .map(p -> {
                    FirestationResponse.Resident dto = new FirestationResponse.Resident();
                    dto.setFirstName(p.getFirstName());
                    dto.setLastName(p.getLastName());
                    dto.setAddress(p.getAddress());
                    dto.setPhone(p.getPhone());
                    return dto;
                })
                .collect(Collectors.toList());

        int numberOfAdults = (int) residents.stream()
                .filter(p -> {
                    MedicalRecord record = findMedicalRecord(p.getFirstName(), p.getLastName());
                    return record != null && calculateAge(record.getBirthdate()) > 18;
                })
                .count();

        int numberOfChildren = residents.size() - numberOfAdults;

        FirestationResponse response = new FirestationResponse();
        response.setResidents(residentDtos);
        response.setNumberOfAdults(numberOfAdults);
        response.setNumberOfChildren(numberOfChildren);

        return response;
    }


    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private MedicalRecord findMedicalRecord(String firstName, String lastName) {
        return dataLoader.getMedicalRecords().stream()
                .filter(record -> record.getFirstName().equals(firstName) && record.getLastName().equals(lastName))
                .findFirst()
                .orElse(null);
    }

}
