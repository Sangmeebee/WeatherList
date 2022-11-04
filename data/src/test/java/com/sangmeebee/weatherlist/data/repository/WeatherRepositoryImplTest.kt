package com.sangmeebee.weatherlist.data.repository

import com.sangmeebee.weatherlist.data.datasource.remote.WeatherRemoteDatasource
import com.sangmeebee.weatherlist.domain.model.Weather
import com.sangmeebee.weatherlist.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {

    private lateinit var weatherRemoteDatasource: WeatherRemoteDatasource
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        weatherRemoteDatasource = mockk()
        weatherRepository = WeatherRepositoryImpl(weatherRemoteDatasource)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `날씨정보를 호출한다`() = runTest {
        // given
        coEvery {
            weatherRepository.getWeather(latitude = 37.5666791, longitude = 126.9782914, appId = "appId")
        } returns Result.success(Weather(city = "Seoul", items = listOf()))
        // when
        weatherRepository.getWeather(latitude = 37.5666791, longitude = 126.9782914, appId = "appId")
        // then
        coVerify { weatherRemoteDatasource.getWeather(latitude = 37.5666791, longitude = 126.9782914, appId = "appId") }
    }
}
