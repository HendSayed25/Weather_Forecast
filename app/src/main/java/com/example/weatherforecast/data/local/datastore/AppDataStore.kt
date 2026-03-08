package com.example.weatherforecast.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDataStore @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    private val dataStore = PreferenceDataStoreFactory.create(
        produceFile = { context.preferencesDataStoreFile("app_prefs") }
    )

    val language: Flow<String> = dataStore.data.map { it[AppPreferences.LANGUAGE_KEY] ?: "en" }

    suspend fun setLanguage(language: String) {
        dataStore.edit { it[AppPreferences.LANGUAGE_KEY] = language }
    }

    val locationLat: Flow<Double> = dataStore.data.map { it[AppPreferences.LOCATION_LAT_KEY] ?: 0.0 }
    val locationLng: Flow<Double> = dataStore.data.map { it[AppPreferences.LOCATION_LNG_KEY] ?: 0.0 }

    suspend fun setLocation(lat: Double, lng: Double) {
        dataStore.edit {
            it[AppPreferences.LOCATION_LAT_KEY] = lat
            it[AppPreferences.LOCATION_LNG_KEY] = lng
        }
    }


    val tempUnit: Flow<TempUnit> = dataStore.data
        .map { prefs ->
            TempUnit.entries.find { it.name == prefs[AppPreferences.TEMP_UNIT_KEY] }
                ?: TempUnit.CELSIUS
        }

    suspend fun setTempUnit(unit: TempUnit) {
        dataStore.edit { it[AppPreferences.TEMP_UNIT_KEY] = unit.name }
    }

    val windSpeedUnit: Flow<WindSpeedUnit> = dataStore.data
        .map { prefs ->
            WindSpeedUnit.entries.find { it.name == prefs[AppPreferences.WIND_SPEED_UNIT_KEY] }
                ?: WindSpeedUnit.METER_PER_SEC
        }

    suspend fun setWindSpeedUnit(unit: WindSpeedUnit) {
        dataStore.edit { it[AppPreferences.WIND_SPEED_UNIT_KEY] = unit.name }
    }


    val locationType: Flow<LocationType> = dataStore.data
        .map { prefs ->
            LocationType.entries.find { it.name == prefs[AppPreferences.LOCATION_TYPE_KEY] }
                ?: LocationType.GPS
        }

    suspend fun setLocationType(type: LocationType) {
        dataStore.edit { it[AppPreferences.LOCATION_TYPE_KEY] = type.name }
    }
}