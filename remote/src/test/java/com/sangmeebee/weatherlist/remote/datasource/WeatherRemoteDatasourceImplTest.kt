package com.sangmeebee.weatherlist.remote.datasource

import com.google.common.truth.Truth.assertThat
import com.sangmeebee.weatherlist.data.datasource.remote.WeatherRemoteDatasource
import com.sangmeebee.weatherlist.remote.exceptions.IllegalAppTokenException
import com.sangmeebee.weatherlist.remote.model.TempResponse
import com.sangmeebee.weatherlist.remote.model.WeatherIconResponse
import com.sangmeebee.weatherlist.remote.model.WeatherItemResponse
import com.sangmeebee.weatherlist.remote.model.WeatherResponse
import com.sangmeebee.weatherlist.remote.model.mapper.toData
import com.sangmeebee.weatherlist.remote.service.WeatherAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    private lateinit var weatherRemoteDatasource: WeatherRemoteDatasource

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        weatherAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)

        weatherRemoteDatasource = WeatherRemoteDatasourceImpl(
            weatherAPI = weatherAPI,
            ioDispatcher = mainDispatcherRule.testDispatcher
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `앱키가 잘못 입력되거나 권한이 없으면 해당하는 예외를 반환한다`() = runTest {
        // given
        val response = MockResponse().setResponseCode(401)
        val expected = IllegalAppTokenException()
        mockWebServer.enqueue(response)
        // when
        weatherRemoteDatasource.getWeather(latitude = 37.5666791, longitude = 126.9782914, appId = "")
            // then
            .onSuccess { actual ->
                assertThat(actual).isInstanceOf(expected::class.java)
            }
            .onFailure { actual ->
                assertThat(actual).isInstanceOf(expected::class.java)
            }
    }

    @Test
    fun `날씨 정보를 반환한다`() = runTest {
        // given
        val response = MockResponse().setResponseCode(200).setBody(File("src/test/resources/weather_200.json").readText())
        mockWebServer.enqueue(response)
        // when
        val actual = weatherRemoteDatasource.getWeather(latitude = 37.5666791, longitude = 126.9782914, appId = "appId")
        // then
        val expected = Result.success(
            WeatherResponse(
                city = "Asia/Seoul",
                items = listOf(
                    WeatherItemResponse(
                        timestamp = 1667530800,
                        tempResponse = TempResponse(
                            min = 3.9,
                            max = 9.21
                        ),
                        weatherIcon = listOf(
                            WeatherIconResponse(
                                iconName = "Clear",
                                iconType = "01d"
                            )
                        )
                    ),
                    WeatherItemResponse(
                        timestamp = 1667617200,
                        tempResponse = TempResponse(
                            min = 4.82,
                            max = 11.76
                        ),
                        weatherIcon = listOf(
                            WeatherIconResponse(
                                iconName = "Clear",
                                iconType = "01d"
                            )
                        )
                    )
                )
            ).toData()
        )

        assertThat(actual).isEqualTo(expected)
    }
}
