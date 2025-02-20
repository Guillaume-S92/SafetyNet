package com.safetynet_alerts.safetynet_alerts.service;

import com.safetynet_alerts.safetynet_alerts.model.Person;
import com.safetynet_alerts.safetynet_alerts.util.DataLoader;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommunityEmailService {

    private final DataLoader dataLoader;

    public CommunityEmailService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public Set<String> getEmailsByCity(String city) {
        // Filtrer les personnes par ville et collecter leurs emails uniques
        return dataLoader.getPersons().stream()
                .filter(person -> person.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .collect(Collectors.toSet());
    }
}
