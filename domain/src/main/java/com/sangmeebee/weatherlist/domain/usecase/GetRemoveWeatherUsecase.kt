package com.sangmeebee.weatherlist.domain.usecase

import com.sangmeebee.weatherlist.domain.model.Weather

class GetRemoveWeatherUsecase {
    operator fun invoke(weathers: List<Weather>): List<Weather> = emptyList()
}
