package com.sangmeebee.weatherlist.domain.usecase

import com.sangmeebee.weatherlist.domain.fake.ErrorFakeGeocoderRepository
import com.sangmeebee.weatherlist.domain.fake.FakeGeocoderRepository
import com.sangmeebee.weatherlist.domain.fake.FakeWeatherRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetWeatherUsecaseTest {

    @Test
    fun `우편번호에 대한 위치정보가 없으면 예외를 반환한다`() = runTest {
        // given
        val getWeatherUsecase = GetWeatherUsecase(FakeGeocoderRepository(), FakeWeatherRepository())
        // when
        getWeatherUsecase(zipCode = "", appId = "appId")
        // then
    }

    @Test
    fun `키값이 유효하지 않으면 예외를 반환한다`() = runTest {
        // given
        val getWeatherUsecase = GetWeatherUsecase(FakeGeocoderRepository(), FakeWeatherRepository())
        // when
        getWeatherUsecase(zipCode = "04524,KR", appId = "")
        // then
    }

    @Test
    fun `경도 위도의 값이 잘못되면 예외를 반환한다`() = runTest {
        // given
        val getWeatherUsecase = GetWeatherUsecase(ErrorFakeGeocoderRepository(), FakeWeatherRepository())
        // when
        getWeatherUsecase(zipCode = "04524,KR", appId = "appId")
        // then
    }

    @Test
    fun `날씨 정보를 반환한다`() = runTest {
        // given
        val getWeatherUsecase = GetWeatherUsecase(FakeGeocoderRepository(), FakeWeatherRepository())
        // when
        getWeatherUsecase("04524,KR", "appId")
        // then
    }
}
