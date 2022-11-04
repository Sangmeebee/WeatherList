package com.sangmeebee.weatherlist.remote

import com.google.common.truth.Truth.assertThat
import com.sangmeebee.weatherlist.remote.model.LocationResponse
import com.sangmeebee.weatherlist.remote.service.GeocoderAPI
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

class GeocoderAPITest {

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
    fun `앱키 쿼리가 비어있거나 잘못된 값이 입력되면 401오류를 반환한다`() = runTest {
        // given
        val response = MockResponse().setResponseCode(401)
        mockWebServer.enqueue(response)
        try {
            // when
            geocoderAPI.getLocation(zipCode = "04524,KR", appId = "")
        } catch (e: HttpException) {
            // then
            assertThat(e.code()).isEqualTo(401)
        }
    }

    @Test
    fun `우편번호가 비어있으면 400오류를 반환한다`() = runTest {
        // given
        val response = MockResponse().setResponseCode(400)
        mockWebServer.enqueue(response)
        try {
            // when
            geocoderAPI.getLocation(zipCode = "", appId = "appId")
        } catch (e: HttpException) {
            // then
            assertThat(e.code()).isEqualTo(400)
        }
    }

    @Test
    fun `우편번호에 대한 위치를 못찾을 경우 404오류를 반환한다`() = runTest {
        // given
        val response = MockResponse().setResponseCode(404)
        mockWebServer.enqueue(response)
        try {
            // when
            geocoderAPI.getLocation(zipCode = "04524,US", appId = "appId")
        } catch (e: HttpException) {
            // then
            assertThat(e.code()).isEqualTo(404)
        }
    }

    @Test
    fun `우편번호에 대한 경도 위도를 반환한다`() = runTest {
        // given
        val response = MockResponse().setBody(File("src/test/resources/200.json").readText())
        mockWebServer.enqueue(response)
        // when
        val actual = geocoderAPI.getLocation(zipCode = "04524,KR", appId = "appId")
        // then
        val expected = LocationResponse(latitude = 37.5662, longitude = 126.9777)
        assertThat(actual).isEqualTo(expected)
    }
}
