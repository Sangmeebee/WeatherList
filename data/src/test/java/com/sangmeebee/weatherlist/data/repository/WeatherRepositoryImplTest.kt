package com.sangmeebee.weatherlist.data.repository

import com.sangmeebee.weatherlist.data.datasource.remote.WeatherRemoteDatasource
import com.sangmeebee.weatherlist.domain.repository.WeatherRepository
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {

    lateinit var weatherRemoteDatasource: WeatherRemoteDatasource
    lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        weatherRemoteDatasource = mockk()
        weatherRepository = WeatherRepositoryImpl(weatherRemoteDatasource)
    }

    @Test
    fun `날씨정보를 호출한다`() {
        // given
        // when
        // then
    }
}
