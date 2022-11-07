package com.sangmeebee.weatherlist.domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FetchWeatherUsecaseTest {

    private lateinit var fetchWeatherUsecase: FetchWeatherUsecase
    private lateinit var getWeatherUsecase: GetWeatherUsecase
    private lateinit var postCacheWeatherUsecase: PostCacheWeatherUsecase
    private lateinit var deleteCacheWeatherUsecase: DeleteCacheWeatherUsecase

    @Before
    fun setUp() {
        getWeatherUsecase = mockk(relaxed = true)
        postCacheWeatherUsecase = mockk(relaxed = true)
        deleteCacheWeatherUsecase = mockk(relaxed = true)
        fetchWeatherUsecase = FetchWeatherUsecase(
            getWeatherUsecase = getWeatherUsecase,
            postCacheWeatherUsecase = postCacheWeatherUsecase,
            deleteCacheWeatherUsecase = deleteCacheWeatherUsecase
        )
    }

    @Test
    fun `우편번호에 대한 날씨 정보를 불러온다`() = runTest {
        // given
        val zipCodes: List<String> = listOf("04524,KR", "E14,GB", "60007,US")
        coEvery { fetchWeatherUsecase(zipCodes, "appId") } returns listOf(Result.success(Unit))
        // when
        val result = fetchWeatherUsecase(zipCodes = zipCodes, appId = "appId")
        // then
        coVerify { getWeatherUsecase(zipCodes[0], "appId") }
        coVerify { getWeatherUsecase(zipCodes[1], "appId") }
        coVerify { getWeatherUsecase(zipCodes[2], "appId") }
        result.forEach { actual ->
            assertThat(actual.isSuccess).isTrue()
        }
    }

    @Test
    fun `우편번호에 대한 날씨 정보를 불러오는 것을 실패하면 오류를 반환한다`() = runTest {
        // given
        val zipCodes: List<String> = listOf("04524,KR", "E14,GB", "60007,US")
        val expected = listOf(Result.success(Unit), Result.failure(Throwable()), Result.success(Unit))
        coEvery { fetchWeatherUsecase(zipCodes, "appId") } returns expected
        coEvery { getWeatherUsecase("04524,KR", "appId") } returns Result.success(emptyList())
        coEvery { getWeatherUsecase("E14,GB", "appId") } returns Result.failure(Throwable())
        coEvery { getWeatherUsecase("60007,US", "appId") } returns Result.success(emptyList())
        // when
        val result = fetchWeatherUsecase(zipCodes = zipCodes, appId = "appId")
        // then
        coVerify { getWeatherUsecase(zipCodes[0], "appId") }
        coVerify { getWeatherUsecase(zipCodes[1], "appId") }
        coVerify { getWeatherUsecase(zipCodes[2], "appId") }
        result.forEachIndexed { index, actual ->
            if (index == 0) {
                assertThat(actual.isFailure).isTrue()
            } else {
                assertThat(actual.isSuccess).isTrue()
            }
        }
    }

    @Test
    fun `날씨 정보를 불러왔으면 로컬 데이터베이스에 저장한다`() = runTest {
        // given
        val zipCodes: List<String> = listOf("04524,KR", "E14,GB", "60007,US")
        coEvery { fetchWeatherUsecase(zipCodes, "appId") } returns listOf(Result.success(Unit))
        // when
        fetchWeatherUsecase(zipCodes = zipCodes, appId = "appId")
        // then
        coVerify { postCacheWeatherUsecase(emptyList()) }
    }

    @Test
    fun `로컬 데이터베이스의 불필요한 날씨 정보를 삭제한다`() = runTest {
        // given
        val zipCodes: List<String> = listOf("04524,KR", "E14,GB", "60007,US")
        coEvery { fetchWeatherUsecase(zipCodes, "appId") } returns listOf(Result.success(Unit))
        // when
        fetchWeatherUsecase(zipCodes = zipCodes, appId = "appId")
        // then
        coVerify { deleteCacheWeatherUsecase() }
    }
}
