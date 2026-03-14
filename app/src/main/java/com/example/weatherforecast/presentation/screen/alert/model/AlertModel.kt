package com.example.weatherforecast.presentation.screen.alert.model

data class AlertModel(
    val id : Int,
    val title : String,
    val timeInMillis : Long,
    val dateInMillis : Long,
    val type : String,
    val condition : String,
    val formattedTime: String,
    val formattedDate: String,
    var isEnable : Boolean
)