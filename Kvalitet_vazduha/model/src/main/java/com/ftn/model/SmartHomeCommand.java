package com.ftn.model;

public class SmartHomeCommand {
    private Long institutionId;
    private boolean closeSmartWindows;
    private String hvacMode;
    private String airPurifierPower;

    // Getters and Setters
    public Long getInstitutionId() { return institutionId; }
    public void setInstitutionId(Long institutionId) { this.institutionId = institutionId; }

    public boolean isCloseSmartWindows() { return closeSmartWindows; }
    public void setCloseSmartWindows(boolean closeSmartWindows) { this.closeSmartWindows = closeSmartWindows; }

    public String getHvacMode() { return hvacMode; }
    public void setHvacMode(String hvacMode) { this.hvacMode = hvacMode; }

    public String getAirPurifierPower() { return airPurifierPower; }
    public void setAirPurifierPower(String airPurifierPower) { this.airPurifierPower = airPurifierPower; }
}