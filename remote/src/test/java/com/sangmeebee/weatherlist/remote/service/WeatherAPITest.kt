package com.sangmeebee.weatherlist.remote.service

import com.google.common.truth.Truth.assertThat
import com.sangmeebee.weatherlist.remote.model.TempResponse
import com.sangmeebee.weatherlist.remote.model.WeatherIconResponse
import com.sangmeebee.weatherlist.remote.model.WeatherItemResponse
import com.sangmeebee.weatherlist.remote.model.WeatherResponse
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class WeatherAPITest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var weatherAPI: WeatherAPI

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        weatherAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `앱키 쿼리가 비어있거나 잘못된 값이 입력되면 401오류를 반환한다`() = runTest {
        // given
        val response = MockResponse().setResponseCode(401)
        mockWebServer.enqueue(response)
        try {
            // when
            weatherAPI.getWeather(latitude = 37.5666791, longitude = 126.9782914, appId = "")
        } catch (e: HttpException) {
            // then
            assertThat(e.code()).isEqualTo(401)
        }
    }

    @Test
    fun `경도 위도를 잘못 전달할 경우 400 에러를 반환한다`() = runTest {
        // given
        mockWebServer.enqueue(MockResponse().setResponseCode(400))
        try {
            // when
            weatherAPI.getWeather(latitude = 999.0, longitude = 126.9782914, appId = "appId")
        } catch (e: HttpException) {
            // then
            assertThat(e.code()).isEqualTo(400)
        }
    }

    @Test
    fun `경도 위도에 대한 날씨 정보를 반환한다`() = runTest {
        // given
        val response = MockResponse().setBody(File("src/test/resources/weather_200.json").readText())
        mockWebServer.enqueue(response)
        // when
        val actual = weatherAPI.getWeather(latitude = 37.5666791, longitude = 126.9782914, appId = "appId")
        // then
        val expected = WeatherResponse(
            items = listOf(
                WeatherItemResponse(
                    timestamp = 1667552400,
                    tempResponse = TempResponse(
                        min = 7.63,
                        max = 9.04
                    ),
                    weatherIcon = listOf(WeatherIconResponse(
                        iconName = "Clouds",
                        iconType = "03n"
                    ))
                ),
                WeatherItemResponse(
                    timestamp = 1667563200,
                    tempResponse = TempResponse(
                        min = 6.22,
                        max = 7.4
                    ),
                    weatherIcon = listOf(WeatherIconResponse(
                        iconName = "Clouds",
                        iconType = "03n"
                    ))
                )
            )
        )
        assertThat(actual).isEqualTo(expected)
    }
}
