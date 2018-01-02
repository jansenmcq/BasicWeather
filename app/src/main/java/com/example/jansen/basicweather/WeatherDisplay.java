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

    private WeatherData weatherData;
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
        this.weatherData = new WeatherData();
        URL url;
        try {
            url = new URL(WEATHER_API + apiKey + "/forecast/geolookup/conditions/q/" + stateCode + "/" + city + ".json");
        } catch (MalformedURLException ex) {
            url = null;
            ex.printStackTrace();
        }
        new FetchWeather(this.weatherData).execute(url);
    }

    public void displayForecast(View view) {
        Intent showForecast = new Intent(this, WeatherDisplay.class);
        showForecast.putExtra("forecast_data", weatherData.getThreeDayForecast());

        startActivity(showForecast);
    }

    private class FetchWeather extends AsyncTask<URL, Void, JSONObject> {

        private WeatherData weatherDataReference;

        public FetchWeather(WeatherData weatherDataReference) {
            this.weatherDataReference = weatherDataReference;
        }

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
                this.weatherDataReference.setLocation(observedLocation.getString("full"));
                WeatherDayData today = new WeatherDayData();
                today.setDay("today");
                today.setDescription(currentObservation.getString("weather"));
                today.setTemperature(currentObservation.getString("temperature_string"));
                this.weatherDataReference.setToday(today);

                JSONObject forecastObject = weatherJSON.getJSONObject("forecast").getJSONObject("simpleforecast");
                JSONArray forecastArray = forecastObject.getJSONArray("forecastday");
                // First day in the array is today, we don't care about forecasting today's weather.
                WeatherDayData[] forecast = new WeatherDayData[3];
                for (int i = 0; i < 3; i++) {
                    WeatherDayData day = new WeatherDayData();
                    JSONObject jsonDay = forecastArray.getJSONObject(i + 1);
                    day.setDay(jsonDay.getJSONObject("date").getString("pretty"));
                    day.setDescription(jsonDay.getString("conditions"));
                    String fahrenheitTemperature = jsonDay.getJSONObject("low").getString("fahrenheit") +
                            "/" + jsonDay.getJSONObject("high").getString("fahrenheit");
                    String celsiusTemperature = jsonDay.getJSONObject("low").getString("celsius") +
                            "/" + jsonDay.getJSONObject("high").getString("celsius");
                    day.setTemperature(fahrenheitTemperature + " F (" + celsiusTemperature + " C)");

                    forecast[i] = day;
                }
                this.weatherDataReference.setThreeDayForecast(forecast);


            } catch (JSONException ex) {
                currentTemperature = weatherDescription = weatherLocation = "There has been an error";
                findViewById(R.id.seeForecastButton).setVisibility(View.INVISIBLE);
                ex.printStackTrace();
            }
            TextView currentLocationView = findViewById(R.id.currentLocation);
            currentLocationView.setText(this.weatherDataReference.getLocation());

            TextView weatherDescriptionView = findViewById(R.id.currentDescription);
            weatherDescriptionView.setText(this.weatherDataReference.getToday().getDescription());

            TextView currentTemperatureView = findViewById(R.id.currentTemperature);
            currentTemperatureView.setText(this.weatherDataReference.getToday().getTemperature());
        }

    }
}
