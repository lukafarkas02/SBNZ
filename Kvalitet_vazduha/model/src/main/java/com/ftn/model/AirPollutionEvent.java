package com.ftn.model;

import com.ftn.model.WindCategory;

public class AirPollutionEvent {
    private double pm25;
    private double pm10;
    private double no2;
    private double windSpeed;
    private WindCategory windCategory;
    private long timestamp; // promenjeno sa LocalDateTime na long

    public AirPollutionEvent(double pm25, double pm10, double no2, double windSpeed, WindCategory windCategory, long timestamp) {
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.no2 = no2;
        this.windSpeed = windSpeed;
        this.windCategory = windCategory;
        this.timestamp = timestamp;
    }

    public AirPollutionEvent() { }

    // Getteri
    public double getPm25() { return pm25; }
    public double getPm10() { return pm10; }
    public double getNo2() { return no2; }
    public long getTimestamp() { return timestamp; }
    public WindCategory getCategory() { return windCategory; }
    public double getWindSpeed() { return windSpeed; }

    // Setteri
    public void setPm25(double pm25) { this.pm25 = pm25; }
    public void setPm10(double pm10) { this.pm10 = pm10; }
    public void setNo2(double no2) { this.no2 = no2; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public void setCategory(WindCategory windCategory) { this.windCategory = windCategory; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }

    @Override
    public String toString() {
        return "AirPollutionEvent [pm25=" + pm25 + ", pm10=" + pm10 + ", no2=" + no2 + ", timestamp=" + timestamp + ", windSpeed= " + windSpeed + "]";
    }
}
