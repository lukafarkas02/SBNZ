package com.ftn.model;

import javax.persistence.*;
import lombok.*;

import com.ftn.model.PollutantType;
import com.ftn.model.AirQualityCategory;

@Entity
@Table(name = "pollutants")
@Data
public class Pollutant{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private PollutantType pollutantType;
    private double value;
    
    @Enumerated(EnumType.STRING)
    private AirQualityCategory status;

    @ManyToOne
    @JoinColumn(name = "measurement_id")
    private Measurement measurement;

    public Pollutant(){}

    public Pollutant(PollutantType pollutantType, double value, AirQualityCategory status, Measurement measurement) {
        this.pollutantType = pollutantType;
        this.value = value;
        this.status = status;
        this.measurement = measurement;
    }

}