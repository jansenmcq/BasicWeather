package com.example.jansen.basicweather;

import android.content.Context;
import android.content.res.Resources;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherFetch {

        private static final String WEATHER_API = "http://api.wunderground.com/api/";

        public static JSONObject getWeather(Context context, String state_code, String city){
            try {
                String apiKey = context.getString(R.string.find_weather);
                URL url = new URL(WEATHER_API + apiKey + "/forecast/geolookup/conditions/q/" + state_code + "/" + city + ".json");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuffer json = new StringBuffer(1024);
                String tmp = "";
                while ((tmp = reader.readLine()) != null) {
                    json.append(tmp).append("\n");
                }
                reader.close();

                JSONObject data = new JSONObject(json.toString());

                // This value will be 404 if the request was not
                // successful
                if (data.getInt("cod") != 200){
                    return null;
                }

                return data;
            } catch (Exception e) {
                return null;
            }
        }

}
