package com.sangmeebee.weatherlist.remote.datasource

import com.google.common.truth.Truth.assertThat
import com.sangmeebee.weatherlist.data.datasource.remote.GeocoderRemoteDatasource
import com.sangmeebee.weatherlist.data.model.LocationEntity
import com.sangmeebee.weatherlist.remote.exceptions.EmptyResultLocationException
import com.sangmeebee.weatherlist.remote.exceptions.IllegalAppTokenException
import com.sangmeebee.weatherlist.remote.exceptions.IllegalLocationException
import com.sangmeebee.weatherlist.remote.service.GeocoderAPI
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

class GeocoderRemoteDatasourceImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var geocoderAPI: GeocoderAPI
    private lateinit var geocoderRemoteDatasource: GeocoderRemoteDatasource

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        geocoderAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocoderAPI::class.java)

        geocoderRemoteDatasource = GeocoderRemoteDatasourceImpl(
            geocoderAPI = geocoderAPI,
            ioDispatcher = mainDispatcherRule.testDispatcher
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `정확하지 않은 우편번호를 입력하면 해당하는 예외를 반환한다`() = runTest {
        // given
        val response = MockResponse().setResponseCode(400)
        val expected = IllegalLocationException()
        mockWebServer.enqueue(response)
        // when
        geocoderRemoteDatasource.getLocation("US", "appId")
            // then
            .onSuccess { actual ->
                assertThat(actual).isInstanceOf(expected::class.java)
            }
            .onFailure { actual ->
                assertThat(actual).isInstanceOf(expected::class.java)
            }
    }

    @Test
    fun `우편번호에 대한 경도 위도가 없다면 해당하는 예외를 반환한다`() = runTest {
        // given
        val response = MockResponse().setResponseCode(404)
        val expected = EmptyResultLocationException()
        mockWebServer.enqueue(response)
        // when
        geocoderRemoteDatasource.getLocation("04524,US", "appId")
            // then
            .onSuccess { actual ->
                assertThat(actual).isInstanceOf(expected::class.java)
            }
            .onFailure { actual ->
                assertThat(actual).isInstanceOf(expected::class.java)
            }
    }

    @Test
    fun `앱키가 잘못 입력되거나 권한이 없으면 해당하는 예외를 반환한다`() = runTest {
        // given
        val response = MockResponse().setResponseCode(401)
        val expected = IllegalAppTokenException()
        mockWebServer.enqueue(response)
        // when
        geocoderRemoteDatasource.getLocation("04524,KR", "")
            // then
            .onSuccess { actual ->
                assertThat(actual).isInstanceOf(expected::class.java)
            }
            .onFailure { actual ->
                assertThat(actual).isInstanceOf(expected::class.java)
            }
    }

    @Test
    fun `위치 정보를 반환한다`() = runTest {
        // given
        val response = MockResponse().setResponseCode(200).setBody(File("src/test/resources/geocoder_200.json").readText())
        mockWebServer.enqueue(response)
        // when
        val actual = geocoderRemoteDatasource.getLocation("04524,KR", "appId")
        // then
        val expected = Result.success(LocationEntity(latitude = 37.5662, longitude = 126.9777))
        assertThat(actual).isEqualTo(expected)
    }
}
