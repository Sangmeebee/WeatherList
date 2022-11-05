package com.sangmeebee.weatherlist.domain.usecase

import com.sangmeebee.weatherlist.domain.model.Weather
import com.sangmeebee.weatherlist.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCacheWeathersFlowUsecase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    operator fun invoke(): Flow<Map<String, List<Weather>>> = weatherRepository.getCacheWeathersFlow()
}
