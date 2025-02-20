package com.safetynet_alerts.safetynet_alerts.service;

import com.safetynet_alerts.safetynet_alerts.model.Firestation;
import com.safetynet_alerts.safetynet_alerts.util.DataLoader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FirestationMappingService {

    private final DataLoader dataLoader;

    public FirestationMappingService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public Firestation addFirestationMapping(Firestation firestation) {
        List<Firestation> firestations = dataLoader.getFirestations();
        firestations.add(firestation);
        return firestation;
    }

    public Firestation updateFirestationMapping(Firestation updatedFirestation) {
        List<Firestation> firestations = dataLoader.getFirestations();
        Optional<Firestation> existingMapping = firestations.stream()
                .filter(firestation -> firestation.getAddress().equalsIgnoreCase(updatedFirestation.getAddress()))
                .findFirst();

        if (existingMapping.isPresent()) {
            Firestation firestation = existingMapping.get();
            firestation.setStation(updatedFirestation.getStation());
            return firestation;
        } else {
            throw new RuntimeException("Firestation mapping not found.");
        }
    }

    public boolean deleteFirestationMapping(String address) {
        List<Firestation> firestations = dataLoader.getFirestations();
        return firestations.removeIf(firestation -> firestation.getAddress().equalsIgnoreCase(address));
    }
}
