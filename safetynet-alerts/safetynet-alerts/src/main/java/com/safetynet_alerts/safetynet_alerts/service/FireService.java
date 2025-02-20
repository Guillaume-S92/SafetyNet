package com.safetynet_alerts.safetynet_alerts.service;

import com.safetynet_alerts.safetynet_alerts.dto.FireResponse;
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
public class FireService {

    private final DataLoader dataLoader;

    public FireService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public FireResponse getFireByAddress(String address) {
        // Récupérer le numéro de la caserne couvrant cette adresse
        String stationNumber = dataLoader.getFirestations().stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .map(Firestation::getStation)
                .findFirst()
                .orElse(null);

        // Récupérer les résidents de cette adresse
        List<FireResponse.ResidentInfo> residents = dataLoader.getPersons().stream()
                .filter(person -> person.getAddress().equals(address))
                .map(person -> {
                    FireResponse.ResidentInfo info = new FireResponse.ResidentInfo();
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

        // Construire la réponse
        FireResponse response = new FireResponse();
        response.setStationNumber(stationNumber);
        response.setResidents(residents);

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
