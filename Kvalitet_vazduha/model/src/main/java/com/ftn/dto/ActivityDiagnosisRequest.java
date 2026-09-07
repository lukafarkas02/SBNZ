package com.ftn.dto;

import com.ftn.model.ActivityType;

public class ActivityDiagnosisRequest {

    private ActivityType activityType;

    private int durationMinutes;

    public ActivityDiagnosisRequest() {
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
} 
