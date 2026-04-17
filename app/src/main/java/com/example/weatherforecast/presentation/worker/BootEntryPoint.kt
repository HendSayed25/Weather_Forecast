package com.example.weatherforecast.presentation.worker

import com.example.weatherforecast.data.repository.WeatherRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface BootEntryPoint {
    fun alertRepository(): WeatherRepository
    fun alertScheduler(): AlertScheduler
}