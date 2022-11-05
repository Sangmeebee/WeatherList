package com.sangmeebee.weatherlist.domain.usecase

import com.sangmeebee.weatherlist.domain.exceptions.DeleteCacheWeatherException
import com.sangmeebee.weatherlist.domain.exceptions.GetCacheWeatherException
import com.sangmeebee.weatherlist.domain.repository.WeatherRepository
import javax.inject.Inject

class DeleteCacheWeatherUsecase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val getRemoveWeatherUsecase: GetRemoveWeatherUsecase,
) {
    suspend operator fun invoke(): Result<Unit> =
        weatherRepository.getAllWeathersAtCache().fold(
            onSuccess = { weatherGroup ->
                val removeWeathers = weatherGroup.values.map { weathers ->
                    getRemoveWeatherUsecase(weathers)
                }
                weatherRepository.deleteWeathersAtCache(removeWeathers.flatten())
                    .onSuccess {
                        Result.success(Unit)
                    }.onFailure {
                        Result.failure<Throwable>(DeleteCacheWeatherException())
                    }
            },
            onFailure = { Result.failure(GetCacheWeatherException()) }
        )
}