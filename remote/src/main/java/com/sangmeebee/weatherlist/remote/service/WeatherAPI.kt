package com.sangmeebee.weatherlist.remote.service

import com.sangmeebee.weatherlist.remote.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface WeatherAPI {
    @GET("data/2.5/forecast")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("long") longitude: Double,
        @Query("appid") appId: String,
        @Query("units") units: String = "metric",
    ): WeatherResponse
}
