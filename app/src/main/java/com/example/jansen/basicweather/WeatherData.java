package com.example.jansen.basicweather;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jansen on 1/1/2018.
 */

public class WeatherData implements Parcelable {

    private String location;
    private WeatherDayData today;
    private WeatherDayData[] threeDayForecast;


    public WeatherData() {}

    public WeatherData(Parcel parcel) {
        this.location = parcel.readString();
        this.today = parcel.readParcelable(WeatherDayData.class.getClassLoader());
        this.threeDayForecast = (WeatherDayData[]) parcel.readParcelableArray(WeatherDayData.class.getClassLoader());
    }

    public static final Creator<WeatherData> CREATOR = new Creator<WeatherData>() {
        @Override
        public WeatherData createFromParcel(Parcel parcel) {
            return new WeatherData(parcel);
        }

        @Override
        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.location);
        parcel.writeParcelable(this.today, 0);
        parcel.writeParcelableArray(this.threeDayForecast, 0);
    }
}
