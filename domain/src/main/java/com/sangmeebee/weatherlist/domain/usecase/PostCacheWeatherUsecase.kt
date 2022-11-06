package com.sangmeebee.weatherlist.domain.usecase

import com.sangmeebee.weatherlist.domain.model.Weather
import com.sangmeebee.weatherlist.domain.repository.WeatherRepository
import javax.inject.Inject

class PostCacheWeatherUsecase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(weathers: List<Weather>): Result<Unit> =
        weatherRepository.insertAllAtCache(weathers)
}
