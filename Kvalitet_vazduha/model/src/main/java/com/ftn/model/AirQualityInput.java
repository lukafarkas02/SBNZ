package com.ftn.model;

import java.time.LocalDateTime;
import com.ftn.model.PollutantMeasurment;
import com.ftn.model.ContextData;
import com.ftn.model.User;

public class AirQualityInput {
    private PollutantMeasurment pollutantMeasurment;
    private WeatherConditions weatherConditions;
    private ContextData contextData;
    private User user;
    private LocalDateTime timestamp;

    public AirQualityInput(PollutantMeasurment pollutantMeasurment,
                           WeatherConditions weatherConditions,
                           ContextData contextData,
                           User user,
                           LocalDateTime timestamp) {
        this.pollutantMeasurment = pollutantMeasurment;
        this.weatherConditions = weatherConditions;
        this.contextData = contextData;
        this.user = user;
        this.timestamp = timestamp;
    }

    // Getteri
    public PollutantMeasurment getPollutantMeasurment() {
        return pollutantMeasurment;
    }

    public WeatherConditions getWeatherConditions() {
        return weatherConditions;
    }

    public ContextData getContextData() {
        return contextData;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Setteri
    public void setPollutantMeasurment(PollutantMeasurment pollutantMeasurment) {
        this.pollutantMeasurment = pollutantMeasurment;
    }

    public void setWeatherConditions(WeatherConditions weatherConditions) {
        this.weatherConditions = weatherConditions;
    }

    public void setContextData(ContextData contextData) {
        this.contextData = contextData;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
