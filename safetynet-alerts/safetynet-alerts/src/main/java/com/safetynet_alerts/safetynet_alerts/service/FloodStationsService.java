package com.safetynet_alerts.safetynet_alerts.service;

import com.safetynet_alerts.safetynet_alerts.dto.FloodStationsResponse;
import com.safetynet_alerts.safetynet_alerts.model.Firestation;
import com.safetynet_alerts.safetynet_alerts.model.MedicalRecord;
import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.util.DataLoader;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FloodStationsService {

    private final DataLoader dataLoader;

    public FloodStationsService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public List<FloodStationsResponse> getFloodStations(List<String> stationNumbers) {
        // Récupérer toutes les adresses couvertes par les casernes spécifiées
        Set<String> addresses = dataLoader.getFirestations().stream()
                .filter(firestation -> stationNumbers.contains(firestation.getStation()))
                .map(Firestation::getAddress)
                .collect(Collectors.toSet());

        // Grouper les résidents par adresse
        Map<String, List<Person>> groupedResidents = dataLoader.getPersons().stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .collect(Collectors.groupingBy(Person::getAddress));

        // Construire la réponse
        List<FloodStationsResponse> response = new ArrayList<>();
        for (Map.Entry<String, List<Person>> entry : groupedResidents.entrySet()) {
            String address = entry.getKey();
            List<FloodStationsResponse.ResidentInfo> residents = entry.getValue().stream()
                    .map(person -> {
                        FloodStationsResponse.ResidentInfo info = new FloodStationsResponse.ResidentInfo();
                        info.setFirstName(person.getFirstName());
                        info.setLastName(person.getLastName());
                        info.setPhone(person.getPhone());

                        MedicalRecord record = findMedicalRecord(person.getFirstName(), person.getLastName());
                        if (record != null) {
                            info.setAge(calculateAge(record.getBirthdate()));
                            info.setMedications(record.getMedications());
                            info.setAllergies(record.getAllergies());
                        }

                        return info;
                    })
                    .collect(Collectors.toList());

            FloodStationsResponse floodResponse = new FloodStationsResponse();
            floodResponse.setAddress(address);
            floodResponse.setResidents(residents);

            response.add(floodResponse);
        }

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
