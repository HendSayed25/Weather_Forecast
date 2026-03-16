package com.example.weatherforecast.data.local.datastore

enum class TempUnit(val labelEn: String, val labelAr: String, val symbol: String) {
    CELSIUS(labelEn = "Celsius", labelAr = "درجة مئوية", symbol = "°C"),
    FAHRENHEIT(labelEn = "Fahrenheit", labelAr = "فهرنهايت", symbol = "°F"),
    KELVIN(labelEn = "Kelvin", labelAr = "كلفن", symbol = "K")
}

enum class WindSpeedUnit(val labelEn: String, val labelAr: String, val symbol: String) {
    METER_PER_SEC("Meter/sec", "متر/ث", "m/s"),
    KILOMETER_PER_HOUR("Kilometer/hour", "كم/س", "km/h"),
    MILE_PER_HOUR("Mile/hour", "ميل/س", "mph")
}

enum class LocationType {
    GPS,
    MAP
}

enum class Language(val englishLabel: String, val arabicLabel: String, val code: String) {
    ENGLISH("English", "الإنجليزية", "en"),
    ARABIC("Arabic", "العربية", "ar")
}