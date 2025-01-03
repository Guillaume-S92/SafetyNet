package com.safetynet_alerts.safetynet_alerts.dto;

import lombok.Data;

import java.util.List;

@Data
public class FirestationResponse {
    private List<Resident> residents;
    private int numberOfAdults;
    private int numberOfChildren;

    @Data
    public static class Resident {
        private String firstName;
        private String lastName;
        private String address;
        private String phone;
    }
}
