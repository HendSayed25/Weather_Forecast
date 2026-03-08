package com.example.weatherforecast.data.local.datastore

enum class TempUnit(val label: String, val symbol: String) {
    CELSIUS(label = "Celsius", symbol = "°C"),
    FAHRENHEIT(label = "Fahrenheit", symbol = "°F"),
    KELVIN(label = "Kelvin", symbol = "K")
}

enum class WindSpeedUnit(val label: String, val symbol: String) {
    METER_PER_SEC(label = "Meter/sec", symbol = "m/s"),
    MILE_PER_HOUR(label = "Mile/hour", symbol = "mph")
}

enum class LocationType {
    GPS,
    MAP
}