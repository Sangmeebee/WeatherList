package com.sangmeebee.weatherlist.domain.usecase

import com.sangmeebee.weatherlist.domain.model.Weather
import com.sangmeebee.weatherlist.domain.repository.GeocoderRepository
import com.sangmeebee.weatherlist.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUsecase @Inject constructor(
    private val geocoderRepository: GeocoderRepository,
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(zipCode: String, appId: String): Result<List<Weather>> =
        geocoderRepository.getLocation(zipCode, appId).fold(
            onSuccess = { location -> weatherRepository.getWeather(location.latitude, location.longitude, appId) },
            onFailure = { throwable -> Result.failure(throwable) }
        )
}
