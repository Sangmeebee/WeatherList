package com.sangmeebee.weatherlist.cache.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.sangmeebee.weatherlist.cache.model.WeatherPref
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherDaoTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var database: AppDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            AppDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun `날씨 리스트를 저장한다`() = runTest(mainDispatcherRule.testDispatcher) {
        // given
        val weathers = fakeInsertWeathers
        val expected = fakeInsertExpectedWeathers
        // when
        database.weatherDao().insertAll(weathers)
        // then
        val actual = database.weatherDao().getAllWeathers()
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `날씨 리스트를 삭제한다`() = runTest(mainDispatcherRule.testDispatcher) {
        // given
        val weathers = fakeInsertWeathers
        // when
        database.weatherDao().insertAll(weathers)
        database.weatherDao().deleteAll(weathers)
        // then
        val actual = database.weatherDao().getAllWeathers()
        assertThat(actual).isEmpty()
    }

    @Test
    fun `날씨 리스트 전체를 불러온다`() = runTest(mainDispatcherRule.testDispatcher) {
        // given
        val weathers = fakeInsertWeathers
        val expected = fakeInsertExpectedWeathers
        // when
        database.weatherDao().insertAll(weathers)
        // then
        val actual = database.weatherDao().getAllWeathers()
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `날씨 리스트 전체를 데이터 스트림으로 불러온다`() = runTest(mainDispatcherRule.testDispatcher) {
        // given
        val weathers = fakeInsertWeathers
        val expected = fakeInsertExpectedWeathers
        // when
        database.weatherDao().insertAll(weathers)
        // then
        val actual = database.weatherDao().getWeathersFlow()
        assertThat(actual.first()).isEqualTo(expected)
    }

    private val fakeInsertWeathers = listOf(
        WeatherPref(
            city = "Seoul", timestamp = 1667617200, tempMin = -2.34, tempMax = 11.63, iconName = "Clear", iconType = "01d"
        ),
        WeatherPref(
            city = "Seoul", timestamp = 1667703600, tempMin = 7.52, tempMax = 13.86, iconName = "Clear", iconType = "01d"
        ),
        WeatherPref(
            city = "Seoul", timestamp = 1667790000, tempMin = 8.35, tempMax = 13.98, iconName = "Rain", iconType = "10d"
        ),
        WeatherPref(
            city = "Seoul", timestamp = 1667876400, tempMin = 8.62, tempMax = 14.43, iconName = "Rain", iconType = "10d"
        ),
        WeatherPref(
            city = "Seoul", timestamp = 1667962800, tempMin = 9.2, tempMax = 13.71, iconName = "Clouds", iconType = "04d"
        ),
        WeatherPref(
            city = "Seoul", timestamp = 1668049200, tempMin = 9.64, tempMax = 16.57, iconName = "Clouds", iconType = "03d"
        ),
        WeatherPref(
            city = "London", timestamp = 1667617200, tempMin = 11.69, tempMax = 19.02, iconName = "Clear", iconType = "01d"
        ),
        WeatherPref(
            city = "London", timestamp = 1667703600, tempMin = 13.8, tempMax = 17.75, iconName = "Rain", iconType = "10d"
        )
    )

    private val fakeInsertExpectedWeathers = mapOf(
        "Seoul" to listOf(
            WeatherPref(
                city = "Seoul", timestamp = 1667617200, tempMin = -2.34, tempMax = 11.63, iconName = "Clear", iconType = "01d"
            ),
            WeatherPref(
                city = "Seoul", timestamp = 1667703600, tempMin = 7.52, tempMax = 13.86, iconName = "Clear", iconType = "01d"
            ),
            WeatherPref(
                city = "Seoul", timestamp = 1667790000, tempMin = 8.35, tempMax = 13.98, iconName = "Rain", iconType = "10d"
            ),
            WeatherPref(
                city = "Seoul", timestamp = 1667876400, tempMin = 8.62, tempMax = 14.43, iconName = "Rain", iconType = "10d"
            ),
            WeatherPref(
                city = "Seoul", timestamp = 1667962800, tempMin = 9.2, tempMax = 13.71, iconName = "Clouds", iconType = "04d"
            ),
            WeatherPref(
                city = "Seoul", timestamp = 1668049200, tempMin = 9.64, tempMax = 16.57, iconName = "Clouds", iconType = "03d"
            )
        ),
        "London" to listOf(
            WeatherPref(
                city = "London", timestamp = 1667617200, tempMin = 11.69, tempMax = 19.02, iconName = "Clear", iconType = "01d"
            ),
            WeatherPref(
                city = "London", timestamp = 1667703600, tempMin = 13.8, tempMax = 17.75, iconName = "Rain", iconType = "10d"
            )
        )
    )
}
