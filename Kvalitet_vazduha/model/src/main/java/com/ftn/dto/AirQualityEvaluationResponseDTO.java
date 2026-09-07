package com.ftn.dto;

import com.ftn.model.AirQualityInfo;
import com.ftn.model.OutdoorActivityPlan;
import com.ftn.model.HealthCheckReminder;
import com.ftn.model.SmartHomeCommand;
import com.ftn.model.WeatherConditions;

import lombok.*;

@Data
public class AirQualityEvaluationResponseDTO {

    private AirQualityInfo airQualityInfo;

    private OutdoorActivityPlan outdoorActivityPlan;

    private HealthCheckReminder healthCheckReminder;

    private SmartHomeCommand smartHomeCommand;

    private WeatherConditions weatherConditions;
}