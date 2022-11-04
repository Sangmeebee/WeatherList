package com.sangmeebee.weatherlist.remote

import com.sangmeebee.weatherlist.remote.service.WeatherAPI
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class WeatherAPITest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var weatherAPI: WeatherAPI

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `경도 위도를 잘못 전달할 경우 400 에러를 반환한다`() {
        // given
        // when
        // then
    }

    @Test
    fun `경도 위도에 대한 날씨 정보를 반환한다`() {
        // given
        // when
        // then
    }
}
