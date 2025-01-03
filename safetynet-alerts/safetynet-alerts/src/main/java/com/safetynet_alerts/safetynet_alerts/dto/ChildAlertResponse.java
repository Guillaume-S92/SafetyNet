package com.safetynet_alerts.safetynet_alerts.dto;

import java.util.List;

public class ChildAlertResponse {
    private List<Child> children;
    private List<OtherMember> otherMembers;

    // Getters and Setters
    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public List<OtherMember> getOtherMembers() {
        return otherMembers;
    }

    public void setOtherMembers(List<OtherMember> otherMembers) {
        this.otherMembers = otherMembers;
    }

    // Classe interne : Child
    public static class Child {
        private String firstName;
        private String lastName;
        private int age;

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

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    // Classe interne : OtherMember
    public static class OtherMember {
        private String firstName;
        private String lastName;

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
    }
}
