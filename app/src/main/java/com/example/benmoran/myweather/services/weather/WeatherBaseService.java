package com.example.benmoran.myweather.services.weather;

import com.example.benmoran.myweather.services.BaseService;

import retrofit2.Callback;

/**
 * Created by Ben Moran on 14/06/16.
 */
public class WeatherBaseService extends BaseService {

    private final String TAG = "WeatherBaseService";
    private final String APP_ID = "7edfcf0a2946ea2595a779e8c4bddb56";
    static WeatherBaseService weatherService = new WeatherBaseService();

    public static WeatherBaseService getInstance() {
        return weatherService;
    }

    public WeatherBaseService() {
        super();
    }

    public void getWeatherData(final String locationName, final Callback<WeatherData> observer) {
        api.getWeatherData(APP_ID, locationName).enqueue(observer);
    }

    public void getWeatherIcon(String iconId, Callback observer) {
        api.getWeatherIcon(iconId).enqueue(observer);
    }
}
