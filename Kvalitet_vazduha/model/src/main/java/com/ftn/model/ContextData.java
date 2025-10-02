package com.ftn.model;

import com.ftn.model.Season;

public class ContextData {
    private int trafficDensity;       // npr. skala 0-10
    private boolean industrialActivity;
    private String timeOfDay;         // jutro, podne, veče
    private Season season;            // ZIMA / LETO

    public ContextData(int trafficDensity, boolean industrialActivity,
                       String timeOfDay, Season season) {
        this.trafficDensity = trafficDensity;
        this.industrialActivity = industrialActivity;
        this.timeOfDay = timeOfDay;
        this.season = season;
    }

    // Getteri
    public int getTrafficDensity() {
        return trafficDensity;
    }

    public boolean isIndustrialActivity() {
        return industrialActivity;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public Season getSeason() {
        return season;
    }

    // Setteri
    public void setTrafficDensity(int trafficDensity) {
        this.trafficDensity = trafficDensity;
    }

    public void setIndustrialActivity(boolean industrialActivity) {
        this.industrialActivity = industrialActivity;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
