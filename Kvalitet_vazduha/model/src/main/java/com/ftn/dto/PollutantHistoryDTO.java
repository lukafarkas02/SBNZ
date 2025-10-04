package com.ftn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PollutantHistoryDTO {
    private LocalDateTime timestamp;
    private Double pm25;
    private Double pm10;
}
