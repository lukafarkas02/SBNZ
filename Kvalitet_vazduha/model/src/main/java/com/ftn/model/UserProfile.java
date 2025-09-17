package com.ftn.model;

import com.ftn.model.UserCategory;

public class UserProfile {
    private UserCategory userType; // "Dete", "Stariji", "Hronični"

    public UserProfile(UserCategory userType) { this.userType = userType; }
    public UserCategory getUserType() { return userType; }
}
