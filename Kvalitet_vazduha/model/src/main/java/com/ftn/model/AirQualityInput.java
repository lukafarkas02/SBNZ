package com.ftn.model;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import com.ftn.model.Measurement;
import com.ftn.model.ContextData;
import com.ftn.model.User;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "air_quality_inputs")
@Data
public class AirQualityInput {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Measurement pollutantMeasurment;

    @OneToOne(cascade = CascadeType.ALL)
    private WeatherConditions weatherConditions;

    @OneToOne(cascade = CascadeType.ALL)
    private ContextData contextData;

    @ManyToOne
    private User user;
    
    private LocalDateTime timestamp;

    public AirQualityInput() {}

    public AirQualityInput(Measurement pollutantMeasurment, WeatherConditions weatherConditions, ContextData contextData, User user, LocalDateTime timestamp) {
        this.pollutantMeasurment = pollutantMeasurment;
        this.weatherConditions = weatherConditions;
        this.contextData = contextData;
        this.user = user;
        this.timestamp = timestamp;
    }

    public Measurement getPollutantMeasurment() {
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

    public void setPollutantMeasurment(Measurement pollutantMeasurment) {
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
