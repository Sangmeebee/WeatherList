package com.sangmeebee.weatherlist.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.sangmeebee.weatherlist.domain.fake.FakeWeatherRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DeleteCacheWeatherUsecaseTest {
    @Test
    fun `날씨 정보가 오늘 이전의 데이터거나 최대 출력개수를 초과하면 삭제한다`() = runTest {
        // given
        val deleteCacheWeatherUsecase =
            DeleteCacheWeatherUsecase(FakeWeatherRepository(), GetRemoveWeatherUsecase(GetCurrentDateUsecase(), ConvertTimestampToDateUsecase()))
        // when
        val actual = deleteCacheWeatherUsecase()
        // then
        assertThat(actual.isSuccess).isTrue()
    }
}
