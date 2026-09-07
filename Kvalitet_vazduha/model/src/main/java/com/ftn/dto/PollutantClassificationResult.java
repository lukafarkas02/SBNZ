package com.ftn.dto;

public class PollutantClassificationResult {

    private String pollutantType;
    private double value;
    private String status;

    public PollutantClassificationResult() {}

    public PollutantClassificationResult(String pollutantType, double value, String status) {
        this.pollutantType = pollutantType;
        this.value = value;
        this.status = status;
    }

    public String getPollutantType() { return pollutantType; }
    public void setPollutantType(String pollutantType) { this.pollutantType = pollutantType; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}