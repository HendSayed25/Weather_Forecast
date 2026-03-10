package com.example.weatherforecast.presentation.screen.home.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {

    fun getCurrentFormattedDate(): String {
        val date = Date()

        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())
        val dayNumberFormat = SimpleDateFormat("d", Locale.getDefault())

        val day = dayFormat.format(date)
        val month = monthFormat.format(date)
        val dayNumber = dayNumberFormat.format(date)

        return "$day,$dayNumber $month"
    }

    fun getTime(dtTxt: String?): String {

        if (dtTxt.isNullOrEmpty()) return "12:00 AM"

        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        val date = inputFormat.parse(dtTxt)
        return outputFormat.format(date?:"12:00")
    }

    fun getDay(dtTxt: String?): String {
        if (dtTxt.isNullOrEmpty()) return ""

        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEEE", Locale.getDefault())

        val date = inputFormat.parse(dtTxt)
        return date?.let { outputFormat.format(it) } ?: ""
    }
}