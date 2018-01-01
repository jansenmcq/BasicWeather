package com.example.jansen.basicweather;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherDisplay extends AppCompatActivity {

    private JSONObject[] threeDayForecast;
    private String weatherLocation;
    private String weatherDescription;
    private String currentTemperature;
    private static final String WEATHER_API = "http://api.wunderground.com/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_display);

        Intent intent = getIntent();
        String stateCode = intent.getStringExtra("state_code");
        String city = intent.getStringExtra("city_name");
        city = city.replace(' ', '_');
        String apiKey = this.getString(R.string.api_key);
        URL url;
        try {
            url = new URL(WEATHER_API + apiKey + "/forecast/geolookup/conditions/q/" + stateCode + "/" + city + ".json");
        } catch (MalformedURLException ex) {
            url = null;
            ex.printStackTrace();
        }
        new FetchWeather().execute(url);
    }

    public void displayForecast(View view) {
        Intent showForecast = new Intent(this, WeatherDisplay.class);
        showForecast.putExtra("forecast_data", threeDayForecast);

        startActivity(showForecast);
    }

    private class FetchWeather extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... urls) {
            try {

                HttpURLConnection connection = (HttpURLConnection) urls[0].openConnection();

                InputStream inputStream = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer json = new StringBuffer(1024);
                String tmp = "";
                while ((tmp = reader.readLine()) != null) {
                    json.append(tmp).append("\n");
                }
                reader.close();

                JSONObject data = new JSONObject(json.toString());
                return data;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(JSONObject weatherJSON) {
            try {
                if (weatherJSON.getJSONObject("response").has("error")) {
                    JSONObject error = weatherJSON.getJSONObject("response").getJSONObject("error");
                    TextView currentLocationView = findViewById(R.id.currentLocation);
                    if (error.has("description")) {
                        currentLocationView.setText(error.getString("description"));
                    } else {
                        currentLocationView.setText("There has been an error");
                    }
                    findViewById(R.id.seeForecastButton).setVisibility(View.INVISIBLE);
                    findViewById(R.id.currentDescription).setVisibility(View.INVISIBLE);
                    findViewById(R.id.currentTemperature).setVisibility(View.INVISIBLE);
                    return;
                }
                JSONObject currentObservation = weatherJSON.getJSONObject("current_observation");
                JSONObject observedLocation = currentObservation.getJSONObject("display_location");
                weatherLocation = observedLocation.getString("full");
                weatherDescription = currentObservation.getString("weather");
                currentTemperature = currentObservation.getString("temperature_string");

                JSONObject forecastObject = weatherJSON.getJSONObject("forecast").getJSONObject("simpleforecast");
                JSONArray forecast = forecastObject.getJSONArray("forecastday");
                // First day in the array is today, we don't care about forecasting today's weather.
                threeDayForecast = new JSONObject[] {
                        forecast.getJSONObject(1),
                        forecast.getJSONObject(2),
                        forecast.getJSONObject(3)
                };
            } catch (JSONException ex) {
                currentTemperature = weatherDescription = weatherLocation = "There has been an error";
                findViewById(R.id.seeForecastButton).setVisibility(View.INVISIBLE);
                ex.printStackTrace();
            }
            TextView currentLocationView = findViewById(R.id.currentLocation);
            currentLocationView.setText(weatherLocation);

            TextView weatherDescriptionView = findViewById(R.id.currentDescription);
            weatherDescriptionView.setText(weatherDescription);

            TextView currentTemperatureView = findViewById(R.id.currentTemperature);
            currentTemperatureView.setText(currentTemperature);
        }

    }
}
