package com.example.jansen.basicweather;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jansen on 1/1/2018.
 */

public class WeatherData {

    private String location;
    private WeatherDayData today;
    private WeatherDayData[] threeDayForecast;


    public WeatherData() {}

    public WeatherDayData getToday() {
        return today;
    }

    public void setToday(WeatherDayData today) {
        this.today = today;
    }

    public WeatherDayData[] getThreeDayForecast() {
        return threeDayForecast;
    }

    public void setThreeDayForecast(WeatherDayData[] threeDayForecast) {
        this.threeDayForecast = threeDayForecast;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
