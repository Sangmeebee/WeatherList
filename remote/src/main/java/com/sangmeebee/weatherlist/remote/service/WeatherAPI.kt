package com.sangmeebee.weatherlist.remote.service

import retrofit2.http.GET
import retrofit2.http.Query

internal interface WeatherAPI {
    @GET("data/2.5/forecast")
    suspend fun getWeather(
        @Query("lat") latitude: String,
        @Query("long") longitude: String,
        @Query("appid") appId: String,
        @Query("units") units: String = "metric",
    )
}
