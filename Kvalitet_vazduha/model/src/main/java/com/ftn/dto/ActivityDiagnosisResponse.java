package com.ftn.dto;

import java.util.ArrayList;
import java.util.List;

public class ActivityDiagnosisResponse {

    private boolean diagnosed;
    private boolean safe;
    private boolean shouldPostpone;
    private boolean strictRestriction;

    private String riskLevel;
    private String recommendation;

    private List<String> reasoningSteps = new ArrayList<>();

    public ActivityDiagnosisResponse() {
    }

    public boolean isDiagnosed() {
        return diagnosed;
    }

    public void setDiagnosed(boolean diagnosed) {
        this.diagnosed = diagnosed;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public boolean isShouldPostpone() {
        return shouldPostpone;
    }

    public void setShouldPostpone(boolean shouldPostpone) {
        this.shouldPostpone = shouldPostpone;
    }

    public boolean isStrictRestriction() {
        return strictRestriction;
    }

    public void setStrictRestriction(boolean strictRestriction) {
        this.strictRestriction = strictRestriction;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public List<String> getReasoningSteps() {
        return reasoningSteps;
    }

    public void setReasoningSteps(List<String> reasoningSteps) {
        this.reasoningSteps = reasoningSteps;
    }
}