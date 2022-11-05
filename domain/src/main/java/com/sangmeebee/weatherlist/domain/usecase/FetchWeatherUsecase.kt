package com.sangmeebee.weatherlist.domain.usecase

import com.sangmeebee.weatherlist.domain.exceptions.PostCacheWeatherException
import javax.inject.Inject

class FetchWeatherUsecase @Inject constructor(
    private val getWeatherUsecase: GetWeatherUsecase,
    private val postCacheWeatherUsecase: PostCacheWeatherUsecase,
    private val deleteCacheWeatherUsecase: DeleteCacheWeatherUsecase,
) {
    suspend operator fun invoke(zipCode: String, appId: String): Result<Unit> =
        getWeatherUsecase(zipCode, appId).fold(
            onSuccess = { weathers ->
                postCacheWeatherUsecase(weathers.take(WEATHER_MAX_COUNT)).fold(
                    onSuccess = { deleteCacheWeatherUsecase() },
                    onFailure = { Result.failure(PostCacheWeatherException()) }
                )
            },
            onFailure = { Result.failure(it) }
        )

    companion object {
        internal const val WEATHER_MAX_COUNT = 6
    }
}
