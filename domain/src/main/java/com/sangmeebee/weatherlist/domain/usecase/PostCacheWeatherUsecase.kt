package com.sangmeebee.weatherlist.domain.usecase

import com.sangmeebee.weatherlist.domain.model.Weather
import com.sangmeebee.weatherlist.domain.repository.WeatherRepository
import javax.inject.Inject

class PostCacheWeatherUsecase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(weathers: List<Weather>): Result<Unit> {
        val refinedTimestampWeathers = weathers.map { weather ->
            weather.copy(
                timestamp = when (weather.city) {
                    SEOUL -> weather.timestamp
                    LONDON -> weather.timestamp + LONDON_TIME_OFFSET - SEOUL_TIME_OFFSET
                    CHICAGO -> weather.timestamp + CHICAGO_TIME_OFFSET - SEOUL_TIME_OFFSET
                    else -> weather.timestamp
                }
            )
        }
        return weatherRepository.insertAllAtCache(refinedTimestampWeathers)
    }

    companion object {
        private const val SEOUL = "Seoul"
        private const val LONDON = "London"
        private const val CHICAGO = "Chicago"
        private const val SEOUL_TIME_OFFSET: Long = 32400
        private const val LONDON_TIME_OFFSET: Long = 0
        private const val CHICAGO_TIME_OFFSET: Long = -21600
    }
}
