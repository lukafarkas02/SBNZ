package com.ftn.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    // Samo za rizične korisnike
    @Enumerated(EnumType.STRING)
    private RiskType riskType;

    // Samo za institucije
    private String institutionName;
    private InstitutionType institutionType;
    private String institutionAddress;

    private String location;
}
