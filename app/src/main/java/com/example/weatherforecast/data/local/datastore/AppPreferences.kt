package com.example.weatherforecast.data.local.datastore

import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object AppPreferences {
    val LOCATION_LAT_KEY = doublePreferencesKey("location_lat")
    val LOCATION_LNG_KEY = doublePreferencesKey("location_lng")
    val LANGUAGE_KEY     = stringPreferencesKey("language")
    val TEMP_UNIT_KEY    = stringPreferencesKey("temp_unit")
    val WIND_SPEED_UNIT_KEY = stringPreferencesKey("wind_speed_unit")
    val LOCATION_TYPE_KEY     = stringPreferencesKey("location_type")
}