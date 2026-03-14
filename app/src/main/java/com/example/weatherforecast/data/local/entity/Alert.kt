package com.example.weatherforecast.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alerts")
data class Alert(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    @ColumnInfo
    val title : String,
    @ColumnInfo
    val time : Long,
    @ColumnInfo
    val date : Long,
    @ColumnInfo
    val type : String,
    @ColumnInfo
    val condition : String,
    @ColumnInfo
    val isEnable : Boolean
)