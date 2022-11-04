package com.sangmeebee.weatherlist.remote.datasource

import com.sangmeebee.weatherlist.remote.service.GeocoderAPI
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

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        geocoderAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocoderAPI::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `오류를 반환한다`() {
        // given
        val response = MockResponse().setResponseCode(404)
        mockWebServer.enqueue(response)
        // when
        // then
    }

    @Test
    fun `위치 정보를 반환한다`() {
        // given
        val response = MockResponse().setResponseCode(200).setBody(File("src/test/resources/geocoder_200.json").readText())
        mockWebServer.enqueue(response)
        // when
        // then
    }
}
