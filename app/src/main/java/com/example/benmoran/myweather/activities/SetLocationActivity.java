package com.example.benmoran.myweather.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.benmoran.myweather.R;
import com.example.benmoran.myweather.adapters.LocationAdapter;

public class SetLocationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final int LOCATION_SELECTED = 200;
    public static final String EXTRAS_LOCATION_NAME = "LOCATION_NAME";
    private final String TAG = "SetLocationActivity";

    ListView lstLocations;
    LocationAdapter locationAdapter;
    String currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);
        loadViews();
        loadData();
    }

    private void loadViews() {
        lstLocations = (ListView) findViewById(R.id.lst_locations);
        lstLocations.setOnItemClickListener(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void loadData() {
        currentLocation = getSharedPreferences("settings", 0).getString(EXTRAS_LOCATION_NAME, "London");
        locationAdapter = new LocationAdapter(getResources().getStringArray(R.array.locations), getLayoutInflater());
        locationAdapter.setCurrentLocation(currentLocation);
        lstLocations.setAdapter(locationAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String locationName = (String) lstLocations.getAdapter().getItem(position);
        Intent intent = new Intent();
        intent.putExtra(EXTRAS_LOCATION_NAME, locationName);
        setResult(LOCATION_SELECTED, intent);
        finish();
    }


}
