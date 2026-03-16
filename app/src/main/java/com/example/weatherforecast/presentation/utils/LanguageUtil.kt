package com.example.weatherforecast.presentation.utils

import android.content.Context
import android.content.res.Configuration
import com.example.weatherforecast.data.local.datastore.Language
import com.example.weatherforecast.data.local.datastore.TempUnit
import com.example.weatherforecast.data.local.datastore.WindSpeedUnit
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

object LanguageUtilUtils {

    fun getDayNameByLocale(dayEn: String, languageCode: String): String {
        return if (languageCode == "ar") {
            when (dayEn) {
                "Monday" -> "الاثنين"
                "Tuesday" -> "الثلاثاء"
                "Wednesday" -> "الأربعاء"
                "Thursday" -> "الخميس"
                "Friday" -> "الجمعة"
                "Saturday" -> "السبت"
                "Sunday" -> "الأحد"
                else -> dayEn
            }
        } else {
            dayEn
        }
    }

    fun formatNumberByLocale(number: Int, languageCode: String): String {
        val locale = Locale(languageCode)
        val nf = NumberFormat.getInstance(locale)
        return nf.format(number)
    }

    fun getWindUnitLabel(language: Language, unit: WindSpeedUnit): String {
        return if (language == Language.ARABIC) unit.labelAr else unit.labelEn
    }

    fun getTempWithLabel(language: Language, unit: TempUnit): String {
        return if (language == Language.ARABIC) unit.labelAr else unit.symbol
    }

    fun formatDateByLanguage(
        dateString: String,
        language: Language,
        inputPattern: String = "EEEE, dd MMM",
        outputPattern: String = "EEEE, dd MMMM"
    ): String {
        return try {
            val inputFormat = SimpleDateFormat(inputPattern, Locale.ENGLISH)
            val date = inputFormat.parse(dateString)

            val locale = when (language) {
                Language.ARABIC -> Locale("ar")
                else -> Locale.ENGLISH
            }

            val outputFormat = SimpleDateFormat(outputPattern, locale)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            dateString
        }
    }

    fun Context.setAppLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        this.resources.updateConfiguration(config, this.resources.displayMetrics)
    }
}