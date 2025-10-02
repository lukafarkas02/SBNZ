package com.ftn.model;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String userType;
    private String riskType;
    private String institutionName;
    private String institutionType;
    private String institutionAddress;
    private String location;
}
