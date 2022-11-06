package com.sangmeebee.weatherlist.cache.datasource

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.sangmeebee.weatherlist.cache.db.AppDatabase
import com.sangmeebee.weatherlist.cache.db.WeatherDao
import com.sangmeebee.weatherlist.cache.exceptions.DeleteCacheWeatherException
import com.sangmeebee.weatherlist.cache.exceptions.GetCacheWeatherException
import com.sangmeebee.weatherlist.cache.exceptions.PostCacheWeatherException
import com.sangmeebee.weatherlist.cache.model.mapper.toPref
import com.sangmeebee.weatherlist.data.datasource.local.WeatherLocalDatasource
import com.sangmeebee.weatherlist.data.model.WeatherEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherLocalDatasourceImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var database: AppDatabase
    private lateinit var weatherDao: WeatherDao
    private lateinit var weatherLocalDatasource: WeatherLocalDatasource

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        weatherDao = mockk()

        weatherLocalDatasource = WeatherLocalDatasourceImpl(database.weatherDao(), mainDispatcherRule.testDispatcher)
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun `성공적으로 저장한다`() = runTest {
        // given
        val weathers = fakeInsertWeathers
        // when
        val actual = weatherLocalDatasource.insertAll(weathers)
        // then
        assertThat(actual.isSuccess).isTrue()
    }

    @Test
    fun `저장에 실패하면 해당하는 예외를 반환한다`() = runTest {
        // given
        weatherLocalDatasource = WeatherLocalDatasourceImpl(weatherDao, mainDispatcherRule.testDispatcher)
        val weathers = fakeInsertWeathers
        coEvery { weatherDao.insertAll(weathers.toPref()) } throws PostCacheWeatherException()
        // when
        weatherLocalDatasource.insertAll(weathers)
            // then
            .onSuccess { actual -> assertThat(actual).isInstanceOf(PostCacheWeatherException::class.java) }
            .onFailure { actual -> assertThat(actual).isInstanceOf(PostCacheWeatherException::class.java) }
    }

    @Test
    fun `삭제를 성공한다`() = runTest {
        // given
        val weathers = fakeInsertWeathers
        // when
        val actual = weatherLocalDatasource.deleteWeathers(weathers)
        // then
        assertThat(actual.isSuccess).isTrue()
    }

    @Test
    fun `삭제에 실패하면 해당 예외를 반환한다`() = runTest {
        // given
        weatherLocalDatasource = WeatherLocalDatasourceImpl(weatherDao, mainDispatcherRule.testDispatcher)
        val weathers = fakeInsertWeathers
        coEvery { weatherDao.deleteWeathers(weathers.toPref()) } throws DeleteCacheWeatherException()
        // when
        weatherLocalDatasource.deleteWeathers(weathers)
            // then
            .onSuccess { actual -> assertThat(actual).isInstanceOf(DeleteCacheWeatherException::class.java) }
            .onFailure { actual -> assertThat(actual).isInstanceOf(DeleteCacheWeatherException::class.java) }
    }

    @Test
    fun `저장한 데이터를 불러온다`() = runTest {
        // given
        val weathers = fakeInsertWeathers
        weatherLocalDatasource.insertAll(weathers)
        // when
        weatherLocalDatasource.getAllWeathers()
            // then
            .onSuccess { actual -> assertThat(actual).isEqualTo(fakeInsertExpectedWeathers) }
            .onFailure { actual -> assertThat(actual).isEqualTo(fakeInsertExpectedWeathers) }
    }

    @Test
    fun `저장한 데이터를 불러오는 것을 실패하면 해당 예외를 반환한다`() = runTest {
        // given
        weatherLocalDatasource = WeatherLocalDatasourceImpl(weatherDao, mainDispatcherRule.testDispatcher)
        coEvery { weatherDao.getAllWeathers() } throws GetCacheWeatherException()
        // when
        weatherLocalDatasource.getAllWeathers()
            // then
            .onSuccess { actual -> assertThat(actual).isInstanceOf(GetCacheWeatherException::class.java) }
            .onFailure { actual -> assertThat(actual).isInstanceOf(GetCacheWeatherException::class.java) }
    }

    @Test
    fun `저장한 데이터를 데이터 스트림으로 불러온다`() = runTest {
        // given
        val weathers = fakeInsertWeathers
        weatherLocalDatasource.insertAll(weathers)
        // when
        val actual = weatherLocalDatasource.getWeathersFlow().first()
        // then
        assertThat(actual).isEqualTo(fakeInsertExpectedWeathers)
    }

    private val fakeInsertWeathers = listOf(
        WeatherEntity(
            city = "Seoul", timestamp = 1667617200, tempMin = -2.34, tempMax = 11.63, iconName = "Clear", iconType = "01d"
        ),
        WeatherEntity(
            city = "Seoul", timestamp = 1667703600, tempMin = 7.52, tempMax = 13.86, iconName = "Clear", iconType = "01d"
        ),
        WeatherEntity(
            city = "Seoul", timestamp = 1667790000, tempMin = 8.35, tempMax = 13.98, iconName = "Rain", iconType = "10d"
        ),
        WeatherEntity(
            city = "Seoul", timestamp = 1667876400, tempMin = 8.62, tempMax = 14.43, iconName = "Rain", iconType = "10d"
        ),
        WeatherEntity(
            city = "Seoul", timestamp = 1667962800, tempMin = 9.2, tempMax = 13.71, iconName = "Clouds", iconType = "04d"
        ),
        WeatherEntity(
            city = "Seoul", timestamp = 1668049200, tempMin = 9.64, tempMax = 16.57, iconName = "Clouds", iconType = "03d"
        ),
        WeatherEntity(
            city = "London", timestamp = 1667617200, tempMin = 11.69, tempMax = 19.02, iconName = "Clear", iconType = "01d"
        ),
        WeatherEntity(
            city = "London", timestamp = 1667703600, tempMin = 13.8, tempMax = 17.75, iconName = "Rain", iconType = "10d"
        )
    )

    private val fakeInsertExpectedWeathers = mapOf(
        "Seoul" to listOf(
            WeatherEntity(
                city = "Seoul", timestamp = 1667617200, tempMin = -2.34, tempMax = 11.63, iconName = "Clear", iconType = "01d"
            ),
            WeatherEntity(
                city = "Seoul", timestamp = 1667703600, tempMin = 7.52, tempMax = 13.86, iconName = "Clear", iconType = "01d"
            ),
            WeatherEntity(
                city = "Seoul", timestamp = 1667790000, tempMin = 8.35, tempMax = 13.98, iconName = "Rain", iconType = "10d"
            ),
            WeatherEntity(
                city = "Seoul", timestamp = 1667876400, tempMin = 8.62, tempMax = 14.43, iconName = "Rain", iconType = "10d"
            ),
            WeatherEntity(
                city = "Seoul", timestamp = 1667962800, tempMin = 9.2, tempMax = 13.71, iconName = "Clouds", iconType = "04d"
            ),
            WeatherEntity(
                city = "Seoul", timestamp = 1668049200, tempMin = 9.64, tempMax = 16.57, iconName = "Clouds", iconType = "03d"
            )
        ),
        "London" to listOf(
            WeatherEntity(
                city = "London", timestamp = 1667617200, tempMin = 11.69, tempMax = 19.02, iconName = "Clear", iconType = "01d"
            ),
            WeatherEntity(
                city = "London", timestamp = 1667703600, tempMin = 13.8, tempMax = 17.75, iconName = "Rain", iconType = "10d"
            )
        )
    )
}
