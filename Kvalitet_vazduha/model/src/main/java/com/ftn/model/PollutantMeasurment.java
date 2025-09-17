package com.ftn.model;

import com.ftn.model.AirQualityCategory;

public class PollutantMeasurment {
    private double pm25;
    private double pm10;
    private double no2;
    private double o3;

    private AirQualityCategory pm25Status;
    private AirQualityCategory pm10Status;
    private AirQualityCategory no2Status;

    public PollutantMeasurment(double pm25, double pm10, double no2, double o3) {
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.no2 = no2;
        this.o3 = o3;
    }

    // getters & setters
    public double getPm25() { return pm25; }
    public double getPm10() { return pm10; }
    public double getNo2() { return no2; }
    public double getO3() { return o3; }

    public AirQualityCategory getPm25Status() { return pm25Status; }
    public void setPm25Status(AirQualityCategory pm25Status) { this.pm25Status = pm25Status; }

    public AirQualityCategory getPm10Status() { return pm10Status; }
    public void setPm10Status(AirQualityCategory pm10Status) { this.pm10Status = pm10Status; }

    public AirQualityCategory getNo2Status() { return no2Status; }
    public void setNo2Status(AirQualityCategory no2Status) { this.no2Status = no2Status; }
}
