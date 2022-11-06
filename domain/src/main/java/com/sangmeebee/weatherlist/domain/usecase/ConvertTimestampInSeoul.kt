package com.sangmeebee.weatherlist.domain.usecase

import com.sangmeebee.weatherlist.domain.model.Weather
import javax.inject.Inject

class ConvertTimestampInSeoul @Inject constructor() {
    operator fun invoke(weathers: List<Weather>): List<Weather> = weathers.map { weather ->
        weather.copy(
            timestamp = when (weather.city) {
                SEOUL -> weather.timestamp
                LONDON -> weather.timestamp + LONDON_TIME_OFFSET - SEOUL_TIME_OFFSET
                CHICAGO -> weather.timestamp + CHICAGO_TIME_OFFSET - SEOUL_TIME_OFFSET
                else -> weather.timestamp
            }
        )
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
