package com.example.benmoran.myweather.services;

import com.example.benmoran.myweather.services.weather.WeatherServiceInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ben on 14/06/16.
 */
public abstract class BaseService {

    Gson gson;
    Retrofit retrofit;
    protected WeatherServiceInterface api;

    public BaseService() {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(WeatherServiceInterface.class);
    }
}
