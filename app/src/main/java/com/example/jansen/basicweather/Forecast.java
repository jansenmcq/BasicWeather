package com.example.jansen.basicweather;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Forecast extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        Intent intent = getIntent();
        Parcelable[] forecastParcel = intent.getParcelableArrayExtra("forecast_data");
        WeatherDayData[] forecast = new WeatherDayData[forecastParcel.length];
        for (int i = 0; i < forecastParcel.length; i++) {
            forecast[i] = (WeatherDayData) forecastParcel[i];
        }

        TextView day1Name = findViewById(R.id.day1Name);
        day1Name.setText(forecast[0].getDay());

        TextView day1Description = findViewById(R.id.day1Description);
        day1Description.setText(forecast[0].getDescription());

        TextView day1Temperature = findViewById(R.id.day1Temperature);
        day1Temperature.setText(forecast[0].getTemperature());
        
        TextView day2Name = findViewById(R.id.day2Name);
        day2Name.setText(forecast[1].getDay());

        TextView day2Description = findViewById(R.id.day2Description);
        day2Description.setText(forecast[1].getDescription());

        TextView day2Temperature = findViewById(R.id.day2Temperature);
        day2Temperature.setText(forecast[1].getTemperature());
        
        TextView day3Name = findViewById(R.id.day3Name);
        day3Name.setText(forecast[2].getDay());

        TextView day3Description = findViewById(R.id.day3Description);
        day3Description.setText(forecast[2].getDescription());

        TextView day3Temperature = findViewById(R.id.day3Temperature);
        day3Temperature.setText(forecast[2].getTemperature());
        
        
    }
}
