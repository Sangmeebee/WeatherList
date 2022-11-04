package com.sangmeebee.weatherlist.remote.datasource

import com.sangmeebee.weatherlist.remote.service.WeatherAPI
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class WeatherRemoteDatasourceImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

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
    fun `오류를 반환한다`() = runTest {
        // given
        val response = MockResponse().setResponseCode(400)
        mockWebServer.enqueue(response)
        // when
        // then
    }

    @Test
    fun `날씨 정보를 반환한다`() = runTest {
        // given
        val response = MockResponse().setResponseCode(200).setBody(File("src/test/resources/weather_200.json").readText())
        mockWebServer.enqueue(response)
        // when
        // then
    }
}
