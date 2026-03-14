package com.example.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.weatherforecast.data.local.entity.Alert
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {

    @Insert
    suspend fun addAlert(alert: Alert): Long

    @Query("SELECT * FROM alerts")
    fun getAllAlerts(): Flow<List<Alert>>

    @Update
    suspend fun updateAlert(updatedAlert: Alert)

    @Delete
    suspend fun deleteAlert(alert: Alert)
}