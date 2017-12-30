package com.example.jansen.basicweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDisplay extends AppCompatActivity {

    private String temperatureString;
    private String weatherDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_display);

        Intent intent = getIntent();
        String stateCode = intent.getStringExtra("state_code");
        String city = intent.getStringExtra("city_name");
        city = city.replace(' ', '_');
        WeatherFetch fetcher = new WeatherFetch();
        JSONObject weatherJSON = fetcher.getWeather(this, stateCode, city);
        try {
            JSONObject currentObservation = weatherJSON.getJSONObject("current_observation");
            temperatureString = currentObservation.getString("temperature_string");
            weatherDescription = currentObservation.getString("weather");

            JSONObject forecastObject = weatherJSON.getJSONObject("forecast").getJSONObject("simpleforecast");
            JSONArray forecast = forecastObject.getJSONArray("forecastday");
            // First day in the array is today, we don't care about forecasting today's weather.
            JSONObject[] threeDayForecasts = new JSONObject[] {
                    forecast.getJSONObject(1),
                    forecast.getJSONObject(2),
                    forecast.getJSONObject(3)
            };


        } catch (JSONException ex) {
            //Do something here
        }

    }
}
