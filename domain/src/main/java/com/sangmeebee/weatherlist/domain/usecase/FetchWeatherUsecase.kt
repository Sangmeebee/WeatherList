package com.sangmeebee.weatherlist.domain.usecase

import javax.inject.Inject

class FetchWeatherUsecase @Inject constructor(
    private val getWeatherUsecase: GetWeatherUsecase,
    private val postCacheWeatherUsecase: PostCacheWeatherUsecase,
    private val deleteCacheWeatherUsecase: DeleteCacheWeatherUsecase,
) {
    suspend operator fun invoke(zipCodes: List<String>, appId: String): List<Result<Unit>> =
        zipCodes.map { zipcode ->
            getWeatherUsecase(zipcode, appId).fold(
                onSuccess = { weathers ->
                    postCacheWeatherUsecase(weathers.take(WEATHER_MAX_COUNT)).fold(
                        onSuccess = { deleteCacheWeatherUsecase() },
                        onFailure = { throwable -> Result.failure(throwable) }
                    )
                },
                onFailure = { Result.failure(it) }
            )
        }

    companion object {
        internal const val WEATHER_MAX_COUNT = 6
    }
}
