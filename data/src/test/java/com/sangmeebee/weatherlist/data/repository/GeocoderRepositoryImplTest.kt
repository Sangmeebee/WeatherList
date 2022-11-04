package com.sangmeebee.weatherlist.data.repository

import com.sangmeebee.weatherlist.data.datasource.remote.GeocoderRemoteDatasource
import com.sangmeebee.weatherlist.data.model.LocationEntity
import com.sangmeebee.weatherlist.domain.repository.GeocoderRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GeocoderRepositoryImplTest {

    lateinit var geocoderRemoteDatasource: GeocoderRemoteDatasource
    lateinit var geocoderRepository: GeocoderRepository

    @Before
    fun setUp() {
        geocoderRemoteDatasource = mockk()
        geocoderRepository = GeocoderRepositoryImpl(geocoderRemoteDatasource)
    }

    @Test
    fun `위치정보를 호출한다`() = runTest {
        // given
        val expected = Result.success(LocationEntity(latitude = 37.5662, longitude = 126.9777).toDomain())
        coEvery { geocoderRepository.getLocation("04524,KR", "appId") } returns expected
        // when
        geocoderRepository.getLocation("04524,KR", "appId")
        // then
        coVerify { geocoderRemoteDatasource.getLocation("04524,KR", "appId") }
    }
}
