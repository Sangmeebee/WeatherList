package com.sangmeebee.weatherlist.data.repository

import com.sangmeebee.weatherlist.data.datasource.local.WeatherLocalDatasource
import com.sangmeebee.weatherlist.data.datasource.remote.WeatherRemoteDatasource
import com.sangmeebee.weatherlist.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {

    private lateinit var weatherRemoteDatasource: WeatherRemoteDatasource
    private lateinit var weatherLocalDatasource: WeatherLocalDatasource
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        weatherRemoteDatasource = mockk()
        weatherLocalDatasource = mockk()
        weatherRepository = WeatherRepositoryImpl(weatherRemoteDatasource, weatherLocalDatasource)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `날씨정보를 호출한다`() = runTest {
        // given
        coEvery {
            weatherRepository.getWeather(latitude = 37.5666791, longitude = 126.9782914, appId = "appId")
        } returns Result.success(mockk())
        // when
        weatherRepository.getWeather(latitude = 37.5666791, longitude = 126.9782914, appId = "appId")
        // then
        coVerify { weatherRemoteDatasource.getWeather(latitude = 37.5666791, longitude = 126.9782914, appId = "appId") }
    }

    @Test
    fun `캐시에 날씨정보를 저장한다`() = runTest {
        // given
        coEvery {
            weatherRepository.insertAllAtCache(emptyList())
        } returns Result.success(Unit)
        // when
        weatherRepository.insertAllAtCache(emptyList())
        // then
        coVerify { weatherLocalDatasource.insertAll(emptyList()) }
    }

    @Test
    fun `캐시에 날씨정보를 삭제한다`() = runTest {
        // given
        coEvery {
            weatherRepository.deleteWeathersAtCache(emptyList())
        } returns Result.success(Unit)
        // when
        weatherRepository.deleteWeathersAtCache(emptyList())
        // then
        coVerify { weatherLocalDatasource.deleteWeathers(emptyList()) }
    }

    @Test
    fun `캐시 저장된 날씨정보를 호출한다`() = runTest {
        // given
        coEvery {
            weatherRepository.getAllWeathersAtCache()
        } returns Result.success(emptyMap())
        // when
        weatherRepository.getAllWeathersAtCache()
        // then
        coVerify { weatherLocalDatasource.getAllWeathers() }
    }

    @Test
    fun `캐시에 저장된 날씨정보를 데이터 스트림으로 반환한다`() = runTest {
        // given
        coEvery {
            weatherRepository.getCacheWeathersFlow()
        } returns flowOf()
        // when
        weatherRepository.getCacheWeathersFlow()
        // then
        coVerify { weatherLocalDatasource.getWeathersFlow() }
    }
}
