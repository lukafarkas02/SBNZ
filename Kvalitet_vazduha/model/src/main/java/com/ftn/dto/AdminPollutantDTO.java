package com.ftn.dto;

import com.ftn.model.AirQualityCategory;
import com.ftn.model.PollutantType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminPollutantDTO {

    private PollutantType pollutantType;
    private double value;
    private AirQualityCategory status;
}
