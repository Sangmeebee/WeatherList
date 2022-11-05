package com.sangmeebee.weatherlist.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.sangmeebee.weatherlist.domain.fake.ErrorFakeGeocoderRepository
import com.sangmeebee.weatherlist.domain.fake.FakeGeocoderRepository
import com.sangmeebee.weatherlist.domain.fake.FakeWeatherRepository
import com.sangmeebee.weatherlist.domain.model.Weather
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetWeatherUsecaseTest {

    @Test
    fun `우편번호에 대한 위치정보가 없으면 예외를 반환한다`() = runTest {
        // given
        val getWeatherUsecase = GetWeatherUsecase(FakeGeocoderRepository(), FakeWeatherRepository())
        // when
        val expected = "invalid zipcode"
        getWeatherUsecase(zipCode = "", appId = "appId")
            // then
            .onSuccess { actual ->
                assertThat(actual).isEqualTo(expected)
            }
            .onFailure { actual ->
                assertThat(actual.message).isEqualTo(expected)
            }
    }

    @Test
    fun `키값이 유효하지 않으면 예외를 반환한다`() = runTest {
        // given
        val getWeatherUsecase = GetWeatherUsecase(FakeGeocoderRepository(), FakeWeatherRepository())
        // when
        val expected = "invalid appid"
        getWeatherUsecase(zipCode = "04524,KR", appId = "")
            // then
            .onSuccess { actual ->
                assertThat(actual).isEqualTo(expected)
            }
            .onFailure { actual ->
                assertThat(actual.message).isEqualTo(expected)
            }
    }

    @Test
    fun `경도 위도의 값이 잘못되면 예외를 반환한다`() = runTest {
        // given
        val getWeatherUsecase = GetWeatherUsecase(ErrorFakeGeocoderRepository(), FakeWeatherRepository())
        // when
        val expected = "invalid location"
        getWeatherUsecase(zipCode = "04524,KR", appId = "appId")
            // then
            .onSuccess { actual ->
                assertThat(actual).isEqualTo(expected)
            }
            .onFailure { actual ->
                assertThat(actual.message).isEqualTo(expected)
            }
    }

    @Test
    fun `날씨 정보를 반환한다`() = runTest {
        // given
        val getWeatherUsecase = GetWeatherUsecase(FakeGeocoderRepository(), FakeWeatherRepository())
        val expected = listOf(
            Weather(
                city = "Seoul",
                timestamp = 1667617200,
                tempMin = -2.34,
                tempMax = 11.63,
                iconName = "Clear",
                iconType = "01d")
        )
        // when
        getWeatherUsecase("04524,KR", "appId")
            // then
            .onSuccess { actual ->
                assertThat(actual).isEqualTo(expected)
            }
            .onFailure { actual ->
                assertThat(actual).isEqualTo(expected)
            }
    }
}
