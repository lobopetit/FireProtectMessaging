package com.google.backend;

/** The object model for the data we are sending through endpoints */
public class MesureBean {

    private Long id;
    private Double latitude;
    private Double longitude;
    private Double temperature;
    private Double humidity;
    private String date;




    public Double getTemperature() {
        return temperature;
    }
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }
    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}