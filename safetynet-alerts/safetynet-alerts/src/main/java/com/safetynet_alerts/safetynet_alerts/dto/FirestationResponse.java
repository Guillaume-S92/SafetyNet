package com.safetynet_alerts.safetynet_alerts.dto;

import java.util.List;

public class FirestationResponse {
    private List<Resident> residents;
    private int numberOfAdults;
    private int numberOfChildren;

    // Getters
    public List<Resident> getResidents() {
        return residents;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    // Setters
    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    // Inner class Resident
    public static class Resident {
        private String firstName;
        private String lastName;
        private String address;
        private String phone;

        // Getters
        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getAddress() {
            return address;
        }

        public String getPhone() {
            return phone;
        }

        // Setters
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
