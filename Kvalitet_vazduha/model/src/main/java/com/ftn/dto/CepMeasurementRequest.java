package com.ftn.dto;

public class CepMeasurementRequest {

    private String email;
    private double pm25;
    private double pm10;
    private double no2;
    private double o3;
    private double co2;
    private double windSpeed;
    private double humidity;
    private double temperature;
    private double pressure;
    private boolean precipitation;

    public CepMeasurementRequest() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getPm25() { return pm25; }
    public void setPm25(double pm25) { this.pm25 = pm25; }

    public double getPm10() { return pm10; }
    public void setPm10(double pm10) { this.pm10 = pm10; }

    public double getNo2() { return no2; }
    public void setNo2(double no2) { this.no2 = no2; }

    public double getO3() { return o3; }
    public void setO3(double o3) { this.o3 = o3; }

    public double getCo2() { return co2; }
    public void setCo2(double co2) { this.co2 = co2; }

    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }

    public double getHumidity() { return humidity; }
    public void setHumidity(double humidity) { this.humidity = humidity; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public double getPressure() { return pressure; }
    public void setPressure(double pressure) { this.pressure = pressure; }

    public boolean isPrecipitation() { return precipitation; }
    public void setPrecipitation(boolean precipitation) { this.precipitation = precipitation; }
}