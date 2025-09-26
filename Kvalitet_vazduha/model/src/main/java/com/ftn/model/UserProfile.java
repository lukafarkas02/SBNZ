package com.ftn.model;

import com.ftn.model.UserCategory;

public class UserProfile {

    private UserCategory userType; // "Dete", "Stariji", "Hronični"

    public UserProfile() {
    }

    public UserProfile(UserCategory userType) {
        this.userType = userType;
    }

    // Getter
    public UserCategory getUserType() {
        return userType;
    }

    // Setter
    public void setUserType(UserCategory userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userType=" + userType +
                '}';
    }

}