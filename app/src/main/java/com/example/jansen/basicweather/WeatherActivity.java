package com.example.jansen.basicweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class WeatherActivity extends AppCompatActivity {

    private final String STATESASSET = "usStates.json";
    private final String CITIESASSET = "usCities.json";
    private JSONObject stateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        JSONObject statesJSON = this.loadJSONFromAsset(this.STATESASSET);
        this.stateData = statesJSON;
        String[] states = this.loadStatesFromJSON(statesJSON);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, states);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.stateInput);
        textView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void findWeather(View view) {
        Intent intent = new Intent(this, WeatherDisplay.class);
        AutoCompleteTextView stateInput = (AutoCompleteTextView) findViewById(R.id.stateInput);
        EditText cityInput = (EditText) findViewById(R.id.cityInput);
        String state = stateInput.getText().toString();
        String city = cityInput.getText().toString();
        String stateCode;
        try {
            stateCode = this.stateData.getString(state);
        } catch (JSONException ex) {
            TextView errorMessage = (TextView) findViewById(R.id.errorMessage);
            errorMessage.setText(R.string.find_weather_state_error);
            return;
        }
        intent.putExtra("state_code", stateCode);
        intent.putExtra("city_name", city);
        startActivity(intent);
    }

    private String[] loadStatesFromJSON(JSONObject statesJson) {
        ArrayList<String> statesArray = new ArrayList<String>();
        Iterator statesIterator = statesJson.keys();
        while (statesIterator.hasNext()) {
            statesArray.add((String)statesIterator.next());
        }
        return statesArray.toArray(new String[statesArray.size()]);
    }

    private JSONObject loadJSONFromAsset(String assetName) {
        String jsonText = null;
        JSONObject jsonObj = null;
        try {
            InputStream is = getAssets().open(assetName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonText = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            jsonObj = new JSONObject(jsonText);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return jsonObj;
    }
}
