package com.sangmeebee.weatherlist.domain.usecase

import com.sangmeebee.weatherlist.domain.model.Weather
import javax.inject.Inject

class FetchWeatherUsecase @Inject constructor(
    private val getWeatherUsecase: GetWeatherUsecase,
    private val postCacheWeatherUsecase: PostCacheWeatherUsecase,
    private val deleteCacheWeatherUsecase: DeleteCacheWeatherUsecase,
) {
    suspend operator fun invoke(zipCodes: List<String>, appId: String): List<Result<Unit>> {
        val postWeathers = mutableListOf<Weather>()
        val results = mutableListOf<Result<Unit>>()
        zipCodes.map { zipcode ->
            getWeatherUsecase(zipcode, appId)
                .onSuccess { weathers ->
                    postWeathers.addAll(weathers.take(WEATHER_MAX_COUNT))
                }
                .onFailure { throwable ->
                    results.add(Result.failure(throwable))
                }
        }

        results.add(
            postCacheWeatherUsecase(postWeathers).fold(
                onSuccess = { deleteCacheWeatherUsecase() },
                onFailure = { throwable -> Result.failure(throwable) }
            )
        )
        return results
    }

    companion object {
        internal const val WEATHER_MAX_COUNT = 6
    }
}
