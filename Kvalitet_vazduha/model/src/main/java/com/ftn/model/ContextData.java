package com.ftn.model;

import javax.persistence.*;
import lombok.*;

import com.ftn.model.Season;
import com.ftn.model.TimeOfDay;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "context_data")
@Data
public class ContextData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int trafficDensity;
    
    private boolean industrialActivity;

    @Enumerated(EnumType.STRING)
    private TimeOfDay timeOfDay;

    @Enumerated(EnumType.STRING)
    private Season season;

    public ContextData(int trafficDensity, boolean industrialActivity, TimeOfDay timeOfDay, Season season) {
        this.trafficDensity = trafficDensity;
        this.industrialActivity = industrialActivity;
        this.timeOfDay = timeOfDay;
        this.season = season;
    }

    public int getTrafficDensity() {
        return trafficDensity;
    }

    public boolean isIndustrialActivity() {
        return industrialActivity;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    public Season getSeason() {
        return season;
    }

    public void setTrafficDensity(int trafficDensity) {
        this.trafficDensity = trafficDensity;
    }

    public void setIndustrialActivity(boolean industrialActivity) {
        this.industrialActivity = industrialActivity;
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
