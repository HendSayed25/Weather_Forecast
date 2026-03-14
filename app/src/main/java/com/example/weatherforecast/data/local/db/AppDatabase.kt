package com.example.weatherforecast.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherforecast.data.local.dao.AlertDao
import com.example.weatherforecast.data.local.dao.WeatherDao
import com.example.weatherforecast.data.local.entity.Alert
import com.example.weatherforecast.data.local.entity.Weather

@Database(entities = [Weather::class, Alert::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao() : WeatherDao
    abstract fun alertDao() : AlertDao
}