package com.example.weatherforecast.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeUtils {

    fun convertToMillis(date: String, time: String, languageCode: String): Long {

        val locale = if (languageCode == "ar") Locale("ar") else Locale.ENGLISH

        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm a", locale)

        return sdf.parse("$date $time")?.time ?: 0L
    }

    fun formatTime(timeMillis: Long, languageCode: String): String {

        val locale = if (languageCode == "ar") Locale("ar") else Locale.ENGLISH

        val sdf = SimpleDateFormat("hh:mm a", locale)

        return sdf.format(Date(timeMillis))
    }

    fun formatDate(dateMillis: Long, languageCode: String): String {

        val locale = if (languageCode == "ar") Locale("ar") else Locale.ENGLISH

        val sdf = SimpleDateFormat("dd/MM/yyyy", locale)

        return sdf.format(Date(dateMillis))
    }

    fun normalizeTime(input: String): String {

        val arabicNumbers = "٠١٢٣٤٥٦٧٨٩"
        val englishNumbers = "0123456789"

        var result = input

        for (i in arabicNumbers.indices) {
            result = result.replace(arabicNumbers[i], englishNumbers[i])
        }

        result = result
            .replace("ص", "AM")
            .replace("م", "PM")

        return result
    }

    fun formatTimeForLanguage(time: String, languageCode: String): String {

        var result = time

        result = if (languageCode == "ar") {
            result
                .replace("AM", "ص")
                .replace("PM", "م")
        } else {
            result
                .replace("ص", "AM")
                .replace("م", "PM")
        }

        return result
    }
}