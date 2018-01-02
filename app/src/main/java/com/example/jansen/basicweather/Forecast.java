package com.example.jansen.basicweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class Forecast extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        Intent intent = getIntent();
        //what are we doing here??
        //TODO: Either implement these classes as parcelable, or store them and retrieve them in internal storage.
//        WeatherDayData[] forecast = intent.getParcelableArrayExtra("forecast_data");
//        try {
//        } catch (JSONException ex)  {
//
//        }
    }
}
