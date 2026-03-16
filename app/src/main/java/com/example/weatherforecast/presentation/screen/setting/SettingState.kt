package com.example.weatherforecast.presentation.screen.setting

import com.example.weatherforecast.data.local.datastore.Language
import com.example.weatherforecast.data.local.datastore.LocationType
import com.example.weatherforecast.data.local.datastore.TempUnit
import com.example.weatherforecast.data.local.datastore.WindSpeedUnit


data class SettingsState(
    val locationType: LocationType = LocationType.GPS,
    val temperatureUnit: TempUnit = TempUnit.CELSIUS,
    val windUnit: WindSpeedUnit = WindSpeedUnit.METER_PER_SEC,
    val language: Language = Language.ENGLISH,
)