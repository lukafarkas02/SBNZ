package com.ftn.model;

import com.ftn.model.AirQualityCategory;

public class AirQualityStatus {
    private AirQualityCategory category; // DOBAR, UMEREN, LOŠ, OPASAN
    private String explanation;
    private String recommendation;

    public AirQualityCategory getCategory() { return category; }
    public void setCategory(AirQualityCategory category) { this.category = category; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
}
