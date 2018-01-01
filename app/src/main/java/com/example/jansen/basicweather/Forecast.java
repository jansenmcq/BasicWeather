package com.example.jansen.basicweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class Forecast extends AppCompatActivity {

    String day1Name, day2Name, day3Name;
    String day1Description, day2Description, day3Description;
    String day1Temperature, day2Temperature, day3Temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
//        try {
//        } catch (JSONException ex)  {
//
//        }
    }
}
