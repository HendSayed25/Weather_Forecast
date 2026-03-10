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

    suspend fun deleteWeather(weatherId: Int) {
        weatherDao.deleteWeather(weatherId)
    }

    fun getAllWeathers(): Flow<List<Weather>> {
        return weatherDao.getAllWeathers()
    }

    suspend fun getFavWeather(id: Int): Weather {
        return weatherDao.getFavWeather(id)
    }
}