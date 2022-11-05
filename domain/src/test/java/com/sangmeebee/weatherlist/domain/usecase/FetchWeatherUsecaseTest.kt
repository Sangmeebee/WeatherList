package com.sangmeebee.weatherlist.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.sangmeebee.weatherlist.domain.fake.FakeGeocoderRepository
import com.sangmeebee.weatherlist.domain.fake.FakeWeatherRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FetchWeatherUsecaseTest {

    @Test
    fun `날씨 정보를 갱신한다`() = runTest {
        // given
        val fetchWeatherUsecase = FetchWeatherUsecase(
            getWeatherUsecase = GetWeatherUsecase(FakeGeocoderRepository(), FakeWeatherRepository()),
            postCacheWeatherUsecase = PostCacheWeatherUsecase(FakeWeatherRepository()),
            deleteCacheWeatherUsecase = DeleteCacheWeatherUsecase(
                weatherRepository = FakeWeatherRepository(),
                getRemoveWeatherUsecase = GetRemoveWeatherUsecase(GetCurrentDateUsecase(), ConvertTimestampToDateUsecase())
            )
        )
        // when
        val actual = fetchWeatherUsecase(zipCode = "04524,KR", appId = "appId")
        // then
        assertThat(actual.isSuccess).isTrue()
    }
}
