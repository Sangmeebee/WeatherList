package com.sangmeebee.weatherlist.domain.usecase

import com.sangmeebee.weatherlist.domain.model.Weather
import com.sangmeebee.weatherlist.domain.usecase.FetchWeatherUsecase.Companion.WEATHER_MAX_COUNT
import javax.inject.Inject

class GetRemoveWeatherUsecase @Inject constructor(
    private val compareTimestampAndCurrentDateUsecase: CompareTimestampWithCurrentDateUsecase,
) {
    operator fun invoke(weathers: List<Weather>): List<Weather> {
        var existCount = WEATHER_MAX_COUNT

        val oldWeathers = weathers.filterIndexed { index, weather ->
            val isOldDate = compareTimestampAndCurrentDateUsecase(weather.city, weather.timestamp)
            if (isOldDate) {
                existCount += 1
            }
            isOldDate || index >= existCount
        }
        return oldWeathers
    }
}
