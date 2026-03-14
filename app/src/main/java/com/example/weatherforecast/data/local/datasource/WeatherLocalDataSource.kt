package com.example.weatherforecast.data.local.datasource

import com.example.weatherforecast.data.local.dao.AlertDao
import com.example.weatherforecast.data.local.dao.WeatherDao
import com.example.weatherforecast.data.local.entity.Alert
import com.example.weatherforecast.data.local.entity.Weather
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherLocalDataSource @Inject constructor(
    private val weatherDao: WeatherDao,
    private val alertDao: AlertDao
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

    suspend fun addAlert(alert: Alert): Long {
        return alertDao.addAlert(alert)
    }

    suspend fun deleteAlert(alert: Alert) {
        alertDao.deleteAlert(alert)
    }

    fun getAllAlerts(): Flow<List<Alert>> {
        return alertDao.getAllAlerts()
    }

    suspend fun updateAlert(alert: Alert) {
        alertDao.updateAlert(alert)
    }
}