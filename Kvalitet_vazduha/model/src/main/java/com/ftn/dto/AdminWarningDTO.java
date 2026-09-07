package com.ftn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminWarningDTO {

    private Long id;

    private String type;
    private String content;
    private LocalDateTime timestamp;

    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String location;
}