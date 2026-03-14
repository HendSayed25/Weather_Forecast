package com.example.weatherforecast.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeUtils {

    fun convertToMillis(date: String, time: String): Long {
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
        return sdf.parse("$date $time")?.time ?: 0L
    }

    fun formatTime(timeMillis: Long): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        return sdf.format(Date(timeMillis))
    }

    fun formatDate(dateMillis: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        return sdf.format(Date(dateMillis))
    }
}