package com.ftn.model;

import java.time.LocalDateTime;

public class AirPollutionEvent {

    private double pm25;
    private double pm10;
    private double no2;
    private double o3;
    private double co2;

    private double windSpeed;         // m/s
    private boolean precipitation;    // true = ima padavina, false = nema
    private double temperature;       // °C
    private double humidity;          // %
    private double pressure;          // hPa

    private long timestamp;  // vreme merenja

    private String userEmail;

    // === Konstruktor bez argumenata (potreban za Drools) ===
    public AirPollutionEvent() {
    }

    // === Konstruktor sa svim poljima ===
    public AirPollutionEvent(double pm25, double pm10, double no2, double o3, double co2,
                             double windSpeed, boolean precipitation,
                             double temperature, double humidity, double pressure,
                             long timestamp, String userEmail) {
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.no2 = no2;
        this.o3 = o3;
        this.co2 = co2;
        this.windSpeed = windSpeed;
        this.precipitation = precipitation;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.timestamp = timestamp;
        this.userEmail = userEmail;
    }

     public AirPollutionEvent(double pm25, double pm10, double no2, double windSpeed, long timestamp, String userEmail) {
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.no2 = no2;
        this.windSpeed = windSpeed;
        this.timestamp = timestamp;
        this.userEmail = userEmail;
     }

    // === Getteri i setteri ===
    public double getPm25() {
        return pm25;
    }

    public void setPm25(double pm25) {
        this.pm25 = pm25;
    }

    public double getPm10() {
        return pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public double getNo2() {
        return no2;
    }

    public void setNo2(double no2) {
        this.no2 = no2;
    }

    public double getO3() {
        return o3;
    }

    public void setO3(double o3) {
        this.o3 = o3;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public boolean isPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(boolean precipitation) {
        this.precipitation = precipitation;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

     public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    @Override
    public String toString() {
        return "AirPollutionEvent{" +
                "pm25=" + pm25 +
                ", pm10=" + pm10 +
                ", no2=" + no2 +
                ", o3=" + o3 +
                ", co2=" + co2 +
                ", windSpeed=" + windSpeed +
                ", precipitation=" + precipitation +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", timestamp=" + timestamp +
                '}';
    }
}
