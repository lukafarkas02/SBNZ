package com.ftn.model;

public class AirPollutionEvent {
    private double pm25;
    private double pm10;
    private double no2;
    private long timestamp; // promenjeno sa LocalDateTime na long

    public AirPollutionEvent(double pm25, double pm10, double no2, long timestamp) {
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.no2 = no2;
        this.timestamp = timestamp;
    }

    public AirPollutionEvent() { }

    // Getteri
    public double getPm25() { return pm25; }
    public double getPm10() { return pm10; }
    public double getNo2() { return no2; }
    public long getTimestamp() { return timestamp; }

    // Setteri
    public void setPm25(double pm25) { this.pm25 = pm25; }
    public void setPm10(double pm10) { this.pm10 = pm10; }
    public void setNo2(double no2) { this.no2 = no2; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "AirPollutionEvent [pm25=" + pm25 + ", pm10=" + pm10 + ", no2=" + no2 + ", timestamp=" + timestamp + "]";
    }
}
