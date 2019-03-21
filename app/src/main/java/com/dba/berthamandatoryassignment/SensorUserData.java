package com.dba.berthamandatoryassignment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

// Model class representing the raw wristband data with specific user data
public class SensorUserData implements Serializable {

    private static final Random rand = new Random();

    // Values of Latitude, Longitude and Noise are randomly generated to simulate a change in position and environment of user
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public SensorUserData(){

    }

    public SensorUserData(int deviceId, double pm25, double pm10, int co2, int o3, double pressure, double temperature, double humidity, String userId) {
        this.deviceId = deviceId;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.co2 = co2;
        this.o3 = o3;
        this.pressure = pressure;
        this.temperature = temperature;
        this.humidity = humidity;

        this.latitude = rand.nextDouble() * 1.001 + 55.6;
        this.longitude = rand.nextDouble() * 1.001 + 12;

        this.utc = new Date().getTime();

        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User: " + userEmail
                + "\n"
                + "Time: " + getDateFormatted()
                + "\n"
                + "Location:"
                + "\n"
                + "Latitude " + latitude + ", longitude " + longitude
                + "\n"
                + "Data:"
                + "\n"
                + "Temp " + temperature + ", CO2 " + co2
                ;
    }


    public static SensorUserData combineData(SensorRawData rawData, String userId){
        return new SensorUserData(rawData.getDeviceId(),
                rawData.getPm25(),
                rawData.getPm10(),
                rawData.getCo2(),
                rawData.getO3(),
                rawData.getPressure(),
                rawData.getHumidity(),
                rawData.getTemperature(),
                userId);
    }


    public int getDeviceId() {
        return deviceId;
    }

    public double getPm25() {
        return pm25;
    }

    public double getPm10() {
        return pm10;
    }

    public int getCo2() {
        return co2;
    }

    public int getO3() {
        return o3;
    }

    public double getPressure() {
        return pressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public long getUtc() {
        return utc;
    }

    public int getNoise() {
        return noise;
    }

    public String getUserId() {
        return userId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public void setPm25(double pm25) {
        this.pm25 = pm25;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public void setO3(int o3) {
        this.o3 = o3;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setUtc(long utc) {
        this.utc = utc;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setNoise(int noise) {
        this.noise = noise;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDateFormatted(){
        Date timestamp = new Date(getUtc());
        return new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(timestamp);
    }

    private String userEmail;
    private int deviceId;
    private double pm25;
    private double pm10;
    private int co2;
    private int o3;
    private double pressure;
    private double temperature;
    private long utc;
    private double latitude;
    private double longitude;
    private double humidity;
    private int noise;
    private String userId;

}
