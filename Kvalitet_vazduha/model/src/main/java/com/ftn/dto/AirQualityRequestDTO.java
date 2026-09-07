package com.ftn.dto;

import com.ftn.model.Measurement;
import com.ftn.model.WeatherConditions;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirQualityRequestDTO {
    private Measurement measurement;
    private String email;
    private WeatherConditions weather;
}
