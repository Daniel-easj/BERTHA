package com.dba.berthamandatoryassignment;

public class User {

    public User(double latitude, double longitude, int noise, String userId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.noise = noise;
        this.userId = userId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getNoise() {
        return noise;
    }

    public void setNoise(int noise) {
        this.noise = noise;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private double latitude;
    private double longitude;
    private int noise;
    private String userId;

}
