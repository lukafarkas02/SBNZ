package com.ftn.model;

import com.ftn.model.UserCategory;

public class BackwardQualityRequest {
    public double pm25;
    public double pm10;
    public double no2;
    public double o3;
    public double co2;          // <-- Додато
    public double windSpeed;
    public double humidity;     // <-- Додато
    public double temperature;  // <-- Додато
    public double pressure;     // <-- Додато
    public boolean precipitation;
    public String userEmail;
}