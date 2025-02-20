package com.safetynet_alerts.safetynet_alerts.service;

import com.safetynet_alerts.safetynet_alerts.model.Firestation;
import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.util.DataLoader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneAlertService {

    private final DataLoader dataLoader;

    public PhoneAlertService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public List<String> getPhoneNumbersByFirestation(String firestationNumber) {
        // Récupérer les adresses couvertes par cette caserne
        List<String> addresses = dataLoader.getFirestations().stream()
                .filter(firestation -> firestation.getStation().equals(firestationNumber))
                .map(Firestation::getAddress)
                .collect(Collectors.toList());

        // Récupérer les numéros de téléphone des résidents à ces adresses
        return dataLoader.getPersons().stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .map(Person::getPhone)
                .distinct() // Supprimer les doublons
                .collect(Collectors.toList());
    }
}
