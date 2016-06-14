package com.example.benmoran.myweather.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.benmoran.myweather.R;
import com.example.benmoran.myweather.helpers.ImageDownloader;
import com.example.benmoran.myweather.services.weather.WeatherBaseService;
import com.example.benmoran.myweather.services.weather.WeatherData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    SharedPreferences settings;
    ProgressDialog progressDialog;

    //Display elements
    TextView lblLocation, lblWeather, lblMinTemp, lblMaxTemp, lblNoLocation;
    ImageView imgWeather;
    WeatherBaseService weatherService;
    LinearLayout detailsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadViews();
        loadData();
    }

    private void loadViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        lblLocation = (TextView) findViewById(R.id.lbl_location);
        lblWeather = (TextView) findViewById(R.id.lbl_weather);
        lblMinTemp = (TextView) findViewById(R.id.lbl_temp_min);
        lblMaxTemp = (TextView) findViewById(R.id.lbl_temp_max);
        imgWeather = (ImageView) findViewById(R.id.img_weather);
        detailsLayout = (LinearLayout) findViewById(R.id.container_details);
        lblNoLocation = (TextView) findViewById(R.id.lbl_no_location);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                loadWeatherData(settings.getString(SetLocationActivity.EXTRAS_LOCATION_NAME, "London"));
                return true;
            case R.id.action_set_location:
                Intent intent = new Intent(this, SetLocationActivity.class);
                startActivityForResult(intent, 0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData() {
        settings = getSharedPreferences("settings", 0);
        weatherService = WeatherBaseService.getInstance();
        loadWeatherData(settings.getString(SetLocationActivity.EXTRAS_LOCATION_NAME, "London"));
    }

    private void loadWeatherData(final String locationName) {
        if (locationName != null) {
            progressDialog.setMessage("Loading weather data for " + locationName);
            progressDialog.show();
            weatherService.getWeatherData(locationName + ",uk", new Callback<WeatherData>() {
                @Override
                public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                    settings.edit().putString(SetLocationActivity.EXTRAS_LOCATION_NAME, locationName).commit();
                    lblLocation.setText(response.body().getName());
                    lblWeather.setText(response.body().getWeather()[0].getDescription());
                    lblMinTemp.setText(convertToTempString(response.body().getMain().getTemp_min()));
                    lblMaxTemp.setText(convertToTempString(response.body().getMain().getTemp_max()));
                    new ImageDownloader(imgWeather).execute(response.body().getWeather()[0].getIcon());
                    progressDialog.dismiss();
                    lblNoLocation.setVisibility(View.GONE);
                    detailsLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<WeatherData> call, Throwable t) {
                    progressDialog.dismiss();
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Could not retrieve weather data")
                            .setMessage("Please try again later!")
                            .show();
                    lblNoLocation.setVisibility(View.VISIBLE);
                    detailsLayout.setVisibility(View.GONE);
                }
            });
        }
    }

    private String convertToTempString(double value) {
        int celsius = (int) Math.round(value - 273.15);
        return String.format("%d \u00B0C", celsius);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case SetLocationActivity.LOCATION_SELECTED:
                String locationName = data.getStringExtra(SetLocationActivity.EXTRAS_LOCATION_NAME);
                loadWeatherData(locationName);
                break;
        }
    }
}
