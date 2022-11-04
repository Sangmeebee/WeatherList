package com.sangmeebee.weatherlist.remote

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File

class GeocoderAPITest {

    private lateinit var mockWebServer: MockWebServer

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
    fun `앱키 쿼리가 비어있거나 잘못된 값이 입력되면 401오류를 반환한다`() = runTest {
        // given
        val response = MockResponse().setResponseCode(401)
        mockWebServer.enqueue(response)
        // when
        // then
    }

    @Test
    fun `우편번호가 비어있으면 400오류를 반환한다`() {
        // given
        val response = MockResponse().setResponseCode(400)
        mockWebServer.enqueue(response)
        // when
        // then
    }

    @Test
    fun `우편번호에 대한 위치를 못찾을 경우 404오류를 반환한다`() {
        // given
        val response = MockResponse().setResponseCode(404)
        mockWebServer.enqueue(response)
        // when
        // then
    }

    @Test
    fun `우편번호에 대한 경도 위도를 반환한다`() {
        // given
        val response = MockResponse().setBody(File("src/test/resources/200.json").readText())
        mockWebServer.enqueue(response)
        // when
        // then
    }
}
