package com.ftn.dto;

public class ThresholdRuleDTO {
    private String category;
    private double minValue;
    private double maxValue;

    public ThresholdRuleDTO() {}

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getMinValue() { return minValue; }
    public void setMinValue(double minValue) { this.minValue = minValue; }

    public double getMaxValue() { return maxValue; }
    public void setMaxValue(double maxValue) { this.maxValue = maxValue; }
}