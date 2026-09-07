package com.ftn.dto;

public class PollutantValueDTO {

    private String pollutantType;
    private double value;

    public PollutantValueDTO() {}

    public String getPollutantType() { return pollutantType; }
    public void setPollutantType(String pollutantType) { this.pollutantType = pollutantType; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
}