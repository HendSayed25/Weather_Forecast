package com.example.weatherforecast.data.repository

import com.example.weatherforecast.data.local.datasource.WeatherLocalDataSource
import com.example.weatherforecast.data.local.entity.Alert
import com.example.weatherforecast.data.remote.datasource.WeatherRemoteDataSource
import com.example.weatherforecast.data.remote.response.ForecastResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class WeatherRepositoryTest {

    private lateinit var localDataSource: WeatherLocalDataSource
    private lateinit var remoteDataSource: WeatherRemoteDataSource
    private lateinit var repository: WeatherRepository

    @Before
    fun setup(){
        localDataSource = mockk()
        remoteDataSource = mockk()
        repository = WeatherRepository( remoteDataSource,localDataSource)
    }

    @Test
    fun getForecastWeather_shouldReturnForecastResponse() = runTest {

        val fakeForecast = ForecastResponse()

        coEvery { remoteDataSource.getForecastWeather(30.0, 31.0) } returns Result.success(fakeForecast)

        val result = repository.getForecastWeather(30.0, 31.0)

        assertEquals(fakeForecast, result.getOrNull())
    }

    @Test
    fun addWeatherToFav_shouldInsertWeatherIntoLocalDataSource() = runTest {

        coEvery { localDataSource.addWeather(any()) } returns Unit

        repository.addWeatherToFav(
            lat = 30.0,
            long = 31.0,
            cityName = "Cairo",
            countryName = "Egypt"
        )

        coVerify { localDataSource.addWeather(match { it.cityName == "Cairo" && it.cityDescription == "Egypt" }) }
    }

    @Test
    fun updateAlert_shouldCallLocalDataSourceUpdateAlert() = runTest {

        coEvery { localDataSource.updateAlert(any()) } returns Unit
        // Given
        val alert = Alert(
            id = 1,
            title = "Rain Alert",
            time = 1710000000000L,
            date = 1710000000000L,
            condition = "Rain",
            type = "alarm",
            isEnable = true
        )

        // When
        repository.updateAlert(alert)

        // Then
        coVerify { localDataSource.updateAlert(alert) }
    }

    @Test
    fun deleteWeatherFromFav_shouldCallDeleteWeather() = runTest {

        coEvery { localDataSource.deleteWeather(any()) } returns Unit

        repository.deleteWeatherFromFav(1)

        coVerify { localDataSource.deleteWeather(1) }
    }
}