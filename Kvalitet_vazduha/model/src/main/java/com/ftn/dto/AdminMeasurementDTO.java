package com.ftn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminMeasurementDTO {

    private Long id;

    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String location;

    private LocalDateTime timestamp;

    private List<AdminPollutantDTO> pollutants;
}