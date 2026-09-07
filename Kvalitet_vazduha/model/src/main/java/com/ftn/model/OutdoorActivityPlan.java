package com.ftn.model;

public class OutdoorActivityPlan {
    private Long userId;
    private boolean cancelOutdoorSports;
    private String suggestedAlternative;
    private String note;

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public boolean isCancelOutdoorSports() { return cancelOutdoorSports; }
    public void setCancelOutdoorSports(boolean cancelOutdoorSports) { this.cancelOutdoorSports = cancelOutdoorSports; }

    public String getSuggestedAlternative() { return suggestedAlternative; }
    public void setSuggestedAlternative(String suggestedAlternative) { this.suggestedAlternative = suggestedAlternative; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
