package com.ftn.dto;

import java.util.List;

public class MaskRecommendationResponse {

    private boolean shouldWearMask;
    private boolean strongAdvice;
    private String message;
    private String riskMessage;

    public MaskRecommendationResponse() {}

    public MaskRecommendationResponse(boolean shouldWearMask, boolean strongAdvice, String message, String riskMessage) {
        this.shouldWearMask = shouldWearMask;
        this.strongAdvice = strongAdvice;
        this.message = message;
        this.riskMessage = riskMessage;
    }

    public boolean isShouldWearMask() {
        return shouldWearMask;
    }

    public void setShouldWearMask(boolean shouldWearMask) {
        this.shouldWearMask = shouldWearMask;
    }

    public boolean isStrongAdvice() {
        return strongAdvice;
    }

    public void setStrongAdvice(boolean strongAdvice) {
        this.strongAdvice = strongAdvice;
    }

    public String getMessage() {   
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRsikMessage() {   
        return riskMessage;
    }

    public void setRiskMessage(String riskMessage) {
        this.riskMessage = riskMessage;
    }

}