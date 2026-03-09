package com.example.weatherforecast.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherforecast.data.local.dao.WeatherDao
import com.example.weatherforecast.data.local.entity.Weather
import kotlin.jvm.java

@Database(entities = [Weather::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao() : WeatherDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "weather_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}