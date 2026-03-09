package com.example.weatherforecast.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Weather(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo
    val cityName: String,
    @ColumnInfo
    val cityDescription: String,
    @ColumnInfo
    val lat: Double,
    @ColumnInfo
    val long: Double
)