package com.example.weatherforecast.data.local.dao

import androidx.room.Dao
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

    @Query("SELECT * FROM favorites WHERE id = :id")
    suspend fun getFavWeather(id : Int) : Weather

    @Query("DELETE FROM favorites WHERE id = :weatherId")
    suspend fun deleteWeather(weatherId : Int)
}