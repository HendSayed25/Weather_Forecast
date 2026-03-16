package com.example.weatherforecast.data.local.datastore

fun TempUnit.toApiUnits(): String = when(this) {
    TempUnit.CELSIUS -> "metric"
    TempUnit.FAHRENHEIT -> "imperial"
    TempUnit.KELVIN -> "standard"
}

fun Language.toApiLang(): String = when(this) {
    Language.ENGLISH -> "en"
    Language.ARABIC -> "ar"
}