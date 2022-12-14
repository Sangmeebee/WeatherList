package com.sangmeebee.weatherlist.domain.usecase

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
                }.flatten()
                if (removeWeathers.isNotEmpty()) {
                    weatherRepository.deleteWeathersAtCache(removeWeathers)
                } else {
                    Result.success(Unit)
                }
            },
            onFailure = { throwable -> Result.failure(throwable) }
        )
}
