package org.example;

import java.util.Map;

public class ClickstreamInfo {
    private String userID;
    private String sessionID;
    private String timeOfDay;
    private String referrerURL;
    private String deviceType;
    private String browserType;
    private boolean purchaseIntent;
    private boolean discountAvailability;
    private boolean stockAvailability;
    private double purchaseAmount;
    private double pageDuration;
    private String[] userDemographics;
    private Map<String, String> geoLocation;

    // Getters and Setters for all fields

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getReferrerURL() {
        return referrerURL;
    }

    public void setReferrerURL(String referrerURL) {
        this.referrerURL = referrerURL;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public boolean isPurchaseIntent() {
        return purchaseIntent;
    }

    public void setPurchaseIntent(boolean purchaseIntent) {
        this.purchaseIntent = purchaseIntent;
    }

    public boolean isDiscountAvailability() {
        return discountAvailability;
    }

    public void setDiscountAvailability(boolean discountAvailability) {
        this.discountAvailability = discountAvailability;
    }

    public boolean isStockAvailability() {
        return stockAvailability;
    }

    public void setStockAvailability(boolean stockAvailability) {
        this.stockAvailability = stockAvailability;
    }

    public double getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(double purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public double getPageDuration() {
        return pageDuration;
    }

    public void setPageDuration(double pageDuration) {
        this.pageDuration = pageDuration;
    }

    public String[] getUserDemographics() {
        return userDemographics;
    }

    public void setUserDemographics(String[] userDemographics) {
        this.userDemographics = userDemographics;
    }

    public Map<String, String> getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(Map<String, String> geoLocation) {
        this.geoLocation = geoLocation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"userID\":\"").append(userID).append("\",")
                .append("\"sessionID\":\"").append(sessionID).append("\",")
                .append("\"timeOfDay\":\"").append(timeOfDay).append("\",")
                .append("\"referrerURL\":\"").append(referrerURL).append("\",")
                .append("\"deviceType\":\"").append(deviceType).append("\",")
                .append("\"browserType\":\"").append(browserType).append("\",")
                .append("\"purchaseIntent\":").append(purchaseIntent).append(",")
                .append("\"discountAvailability\":").append(discountAvailability).append(",")
                .append("\"stockAvailability\":").append(stockAvailability).append(",")
                .append("\"purchaseAmount\":").append(purchaseAmount).append(",")
                .append("\"pageDuration\":").append(pageDuration).append(",")
                .append("\"userDemographics\":[")
                .append("\"").append(String.join("\",\"", userDemographics)).append("\"")
                .append("],")
                .append("\"geoLocation\":{")
                .append("\"Lon\":\"").append(geoLocation.get("Lon")).append("\",")
                .append("\"Lat\":\"").append(geoLocation.get("Lat")).append("\"")
                .append("}")
                .append("}");

        return sb.toString();
    }

}
