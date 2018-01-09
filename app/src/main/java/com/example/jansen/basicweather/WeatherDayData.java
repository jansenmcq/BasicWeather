package com.example.jansen.basicweather;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jansen on 1/1/2018.
 */

public class WeatherDayData implements Parcelable {

    private String day;
    private String temperature;
    private String description;

    public WeatherDayData() {}

    public WeatherDayData(Parcel parcel) {
        this.day = parcel.readString();
        this.temperature = parcel.readString();
        this.description = parcel.readString();
    }

    public static final Creator<WeatherDayData> CREATOR = new Creator<WeatherDayData>() {
        @Override
        public WeatherDayData createFromParcel(Parcel parcel) {
            return new WeatherDayData(parcel);
        }

        @Override
        public WeatherDayData[] newArray(int size) {
            return new WeatherDayData[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.day);
        parcel.writeString(this.temperature);
        parcel.writeString(this.description);
    }


}
