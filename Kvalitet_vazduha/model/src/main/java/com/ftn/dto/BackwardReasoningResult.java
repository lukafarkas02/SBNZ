package com.ftn.model;

import java.util.ArrayList;
import java.util.List;

public class BackwardReasoningResult {

    private boolean isHazardous;
    private boolean strongProtectAdvice;
    private String explanation;          
    private String recommendation;

    public BackwardReasoningResult() {
    }

    // Konstruktor sa svim parametrima
    public BackwardReasoningResult(boolean isHazardous, boolean strongProtectAdvice, String explanation, String recommendation) {
        this.isHazardous = isHazardous;
        this.strongProtectAdvice = strongProtectAdvice;
        this.explanation = explanation;
        this.recommendation = recommendation;
    }

    // ======================= Getteri i Setteri =======================

    public boolean isHazardous() {
        return isHazardous;
    }

    public void setHazardous(boolean isHazardous) {
        this.isHazardous = isHazardous;
    }

    public boolean isStrongProtectAdvice() {
        return strongProtectAdvice;
    }

    public void setStrongProtectAdvice(boolean strongProtectAdvice) {
        this.strongProtectAdvice = strongProtectAdvice;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    @Override
    public String toString() {
        return "BackwardReasoningResult{" +
                "isHazardous=" + isHazardous +
                ", strongProtectAdvice=" + strongProtectAdvice +
                ", explanation='" + explanation + '\'' +
                ", recommendation='" + recommendation + '\'' +
                '}';
    }
    
}
