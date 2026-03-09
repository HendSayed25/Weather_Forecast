package com.example.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecast.data.local.entity.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addWeather(weather : Weather)

    @Query("SELECT * FROM favorites")
    fun getAllWeathers() : Flow<List<Weather>>

    @Delete
    suspend fun deleteWeather(weather : Weather)
}