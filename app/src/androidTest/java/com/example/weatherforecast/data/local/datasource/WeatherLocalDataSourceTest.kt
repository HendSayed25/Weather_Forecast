package com.example.weatherforecast.data.local.datasource

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.weatherforecast.data.local.dao.AlertDao
import com.example.weatherforecast.data.local.dao.WeatherDao
import com.example.weatherforecast.data.local.db.AppDatabase
import com.example.weatherforecast.data.local.entity.Alert
import com.example.weatherforecast.data.local.entity.Weather
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test

class WeatherLocalDataSourceTest {

    private lateinit var weatherDao: WeatherDao
    private lateinit var alertDao: AlertDao
    private lateinit var db: AppDatabase
    private lateinit var weatherLocalDataSource: WeatherLocalDataSource

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Application>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        weatherDao = db.weatherDao()
        alertDao = db.alertDao()
        weatherLocalDataSource = WeatherLocalDataSource(weatherDao, alertDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addWeather_shouldInsertWeatherIntoDatabase() = runTest {
        //Given
        val weather = Weather(
            id = 1,
            cityName = "Cairo",
            cityDescription = "Sunny",
            lat = 30.0444,
            long = 31.2357
        )

        //when
        weatherLocalDataSource.addWeather(weather)

        //then
        val expectedWeather = weatherLocalDataSource.getFavWeather(1)
        assertThat(expectedWeather.id, `is`(weather.id))
    }

    @Test
    fun deleteWeather_shouldDeleteWeatherFromDatabase() = runTest {
        //Given
        val weather = Weather(
            id = 1,
            cityName = "Cairo",
            cityDescription = "Sunny",
            lat = 30.0444,
            long = 31.2357
        )
        weatherLocalDataSource.addWeather(weather)

        //when
        weatherLocalDataSource.deleteWeather(weather.id)

        //then
        val weathers = weatherLocalDataSource.getAllWeathers().first()
        assert(!weathers.contains(weather))

    }

    @Test
    fun getAllAlerts_shouldReturnAllAlertsFromDatabase() = runTest {
        //Given
        val alert = Alert(
            id = 1,
            title = "Rain Alert",
            time = 1710000000000L,
            date = 1710000000000L,
            type = "Rain",
            condition = "Heavy Rain",
            isEnable = true
        )
        val alert2 = Alert(
            id = 2,
            title = "Rain Alert",
            time = 1710000000000L,
            date = 1710000000000L,
            type = "Cloud",
            condition = "Heavy Rain",
            isEnable = true
        )
        weatherLocalDataSource.addAlert(alert)
        weatherLocalDataSource.addAlert(alert2)

        //when
        val alerts = weatherLocalDataSource.getAllAlerts().first()

        //then
        assertThat(alerts.size, `is`(2))
    }

    @Test
    fun addAlert_shouldInsertAlertIntoDatabase() = runTest {
        //Given
        val alert = Alert(
            id = 1,
            title = "Rain Alert",
            time = 1710000000000L,
            date = 1710000000000L,
            type = "Rain",
            condition = "Heavy Rain",
            isEnable = true
        )

        //when
        weatherLocalDataSource.addAlert(alert)

        //then
        val alerts = weatherLocalDataSource.getAllAlerts().first()
        assert(alerts.contains(alert))
    }

}