package com.sangmeebee.weatherlist.data.repository

import com.sangmeebee.weatherlist.data.datasource.remote.GeocoderRemoteDatasource
import com.sangmeebee.weatherlist.domain.repository.GeocoderRepository
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
    fun `위치정보를 반환한다`() = runTest {
        // given
        // when
        // then
    }
}
