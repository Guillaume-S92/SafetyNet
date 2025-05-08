package com.safetynet_alerts.safetynet_alerts.model;

public class Firestation {
    private String address;
    private String station;

    //  Ajout du constructeur avec arguments
    public Firestation(String address, String station) {
        this.address = address;
        this.station = station;
    }

    //  Constructeur sans arguments
    public Firestation() {}

    // Getters
    public String getAddress() {
        return address;
    }

    public String getStation() {
        return station;
    }

    // Setters
    public void setAddress(String address) {
        this.address = address;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
