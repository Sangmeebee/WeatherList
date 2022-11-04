package com.sangmeebee.weatherlist.remote.service

import com.sangmeebee.weatherlist.remote.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface WeatherAPI {
    @GET("data/3.0/onecall")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appId: String,
        @Query("exclude") exclude: String = "current,minutely,hourly,alerts",
        @Query("units") units: String = "metric",
    ): WeatherResponse
}
