package com.example.senderosdecadiz.models;

import java.io.Serializable;

public class Tiempo implements Serializable {

    String icon;
    String description;
    double temp;
    double wind;
    int humidity;
    long sunset;

    public Tiempo(String icon, String description, double temp, double wind, int humidity, long sunset) {
        this.icon = "http://openweathermap.org/img/wn/"+icon+"@2x.png";
        this.description = description;
        this.temp = temp;
        this.wind = wind;
        this.humidity = humidity;
        this.sunset = sunset;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemp() { return temp; }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getWind() { return wind; }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
}
