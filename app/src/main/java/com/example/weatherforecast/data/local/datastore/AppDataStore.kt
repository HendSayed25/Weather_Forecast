package com.example.weatherforecast.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.weatherforecast.data.remote.model.Coordinate
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = PreferenceDataStoreFactory.create(
        produceFile = { context.preferencesDataStoreFile("app_prefs") }
    )

    val language: Flow<Language> = dataStore.data.map {
        val name = it[AppPreferences.LANGUAGE_KEY]  ?: Language.ENGLISH.name
        Language.valueOf(name)
    }

    suspend fun setLanguage(language: Language) {
        dataStore.edit { it[AppPreferences.LANGUAGE_KEY] = language.name }
    }

    val location: Flow<Coordinate> = dataStore.data
        .map { prefs ->
            Coordinate(
                lat = prefs[AppPreferences.LOCATION_LAT_KEY] ?: 21.422525,
                long = prefs[AppPreferences.LOCATION_LNG_KEY] ?: 39.826181
            )
        }

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