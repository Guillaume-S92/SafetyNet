package com.safetynet_alerts.safetynet_alerts.dto;

import java.util.List;

public class FireResponse {
    private String stationNumber;
    private List<ResidentInfo> residents;

    public static class ResidentInfo {
        private String firstName;
        private String lastName;
        private String phone;
        private int age;
        private List<String> medications;
        private List<String> allergies;

        // Getters and Setters
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<String> getMedications() {
            return medications;
        }

        public void setMedications(List<String> medications) {
            this.medications = medications;
        }

        public List<String> getAllergies() {
            return allergies;
        }

        public void setAllergies(List<String> allergies) {
            this.allergies = allergies;
        }
    }

    // Getters and Setters
    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    public List<ResidentInfo> getResidents() {
        return residents;
    }

    public void setResidents(List<ResidentInfo> residents) {
        this.residents = residents;
    }
}
