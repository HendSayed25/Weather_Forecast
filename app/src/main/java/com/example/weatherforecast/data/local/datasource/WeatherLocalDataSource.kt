package com.example.weatherforecast.data.local.datasource

import com.example.weatherforecast.data.local.dao.WeatherDao
import com.example.weatherforecast.data.local.entity.Weather
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherLocalDataSource @Inject constructor(
    private val weatherDao: WeatherDao
) {

    suspend fun addWeather(weather: Weather) {
        weatherDao.addWeather(weather)
    }

    suspend fun deleteWeather(weather: Weather) {
        weatherDao.deleteWeather(weather)
    }

    fun getAllWeathers(): Flow<List<Weather>> {
        return weatherDao.getAllWeathers()
    }
}