package com.ftn.model;

import com.ftn.model.UserCategory;

public class BackwardEvalRequest {
    public double pm25;
    public double pm10;
    public double no2;
    public double o3;
    public double windSpeed;
    public boolean precipitation;
    public UserCategory userCategory; // CHILD, ELDERLY, CHRONIC, PREGNANT, ADULT
}