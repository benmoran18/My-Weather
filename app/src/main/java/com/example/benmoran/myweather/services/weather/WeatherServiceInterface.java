package com.example.benmoran.myweather.services.weather;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ben Moran on 14/06/16.
 */
public interface WeatherServiceInterface {
    @GET("http://api.openweathermap.org/data/2.5/weather")
    Call<WeatherData> getWeatherData(@Query("APPID") String apiKey, @Query("q") String location);

    @GET("http://openweathermap.org/img/w/{icon}.png")
    Call<Response> getWeatherIcon(@Path("icon") String iconId);
}
