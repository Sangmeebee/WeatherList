package com.sangmeebee.weatherlist.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.sangmeebee.weatherlist.domain.fake.FakeWeatherRepository
import com.sangmeebee.weatherlist.domain.model.Weather
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PostCacheWeatherUsecaseTest {

    @Test
    fun `날씨 리스트를 저장한다`() = runTest {
        // given
        val postCacheWeatherUsecase = PostCacheWeatherUsecase(FakeWeatherRepository())
        val weathers = listOf(
            Weather(
                city = "Seoul",
                timestamp = 4088618200, // 2099-7-25
                tempMin = -2.34,
                tempMax = 11.63,
                iconName = "Clear",
                iconType = "01d"
            )
        )
        // when
        val actual = postCacheWeatherUsecase(weathers)
        // then
        assertThat(actual.isSuccess).isTrue()
    }
}
