package com.ftn.dto;

import java.util.ArrayList;
import java.util.List;

public class MaskRecommendationResponse {

    private boolean shouldWearMask;
    private boolean strongAdvice;
    private String message;
    private String riskMessage;
    private List<String> reasoningSteps = new ArrayList<>(); // <-- Додата листа за кораке backward анализе

    public MaskRecommendationResponse() {}

    public MaskRecommendationResponse(boolean shouldWearMask, boolean strongAdvice, String message, String riskMessage, List<String> reasoningSteps) {
        this.shouldWearMask = shouldWearMask;
        this.strongAdvice = strongAdvice;
        this.message = message;
        this.riskMessage = riskMessage;
        if (reasoningSteps != null) {
            this.reasoningSteps = reasoningSteps;
        }
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

    // Исправљен назив гетера (са getRsikMessage на getRiskMessage)
    public String getRiskMessage() {   
        return riskMessage;
    }

    public void setRiskMessage(String riskMessage) {
        this.riskMessage = riskMessage;
    }

    public List<String> getReasoningSteps() {
        return reasoningSteps;
    }

    public void setReasoningSteps(List<String> reasoningSteps) {
        this.reasoningSteps = reasoningSteps;
    }
}