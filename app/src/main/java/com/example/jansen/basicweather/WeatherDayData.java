package com.example.jansen.basicweather;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jansen on 1/1/2018.
 */

public class WeatherDayData {

    private String day;
    private String temperature;
    private String description;

    public WeatherDayData() {}


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
