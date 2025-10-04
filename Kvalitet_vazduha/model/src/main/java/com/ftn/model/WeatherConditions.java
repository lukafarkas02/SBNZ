package com.ftn.model;

import javax.persistence.*;
import lombok.*;

import com.ftn.model.WindCategory;

@Entity
@Table(name = "weather_conditions")
@Data
public class WeatherConditions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double temperature;   // °C
    private double humidity;      // %
    private double windSpeed;     // m/s
    @Enumerated(EnumType.STRING)
    private WindCategory windCategory;
    private double precipitation; // mm
    private double pressure;      // hPa

    public WeatherConditions(double temperature, double humidity, double windSpeed,
                             double precipitation, double pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.precipitation = precipitation;
        this.pressure = pressure;
    }

    // Getteri
    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public WindCategory getWindCategory() {
        return windCategory;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public double getPressure() {
        return pressure;
    }

    // Setteri
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindCategory(WindCategory windCategory) {
        this.windCategory = windCategory;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
}
