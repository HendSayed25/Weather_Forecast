package com.example.weatherforecast.presentation.screen.map

import androidx.lifecycle.SavedStateHandle
import com.example.weatherforecast.R
import com.example.weatherforecast.data.local.datastore.AppDataStore
import com.example.weatherforecast.data.local.datastore.LocationType
import com.example.weatherforecast.data.remote.model.Address
import com.example.weatherforecast.data.remote.model.Coordinate
import com.example.weatherforecast.data.repository.LocationRepository
import com.example.weatherforecast.data.repository.WeatherRepository
import com.example.weatherforecast.presentation.screen.shared.UiEvent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MapViewModelTest {

    private lateinit var locationRepository: LocationRepository
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var appDataStore: AppDataStore
    private lateinit var viewModel: MapViewModel
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        locationRepository = mockk()
        weatherRepository = mockk()
        appDataStore = mockk()
        val savedStateHandle = SavedStateHandle(mapOf("isFromSetting" to true))

        coEvery { appDataStore.location } returns flowOf(Coordinate(21.422525, 39.826181))

        viewModel = MapViewModel(locationRepository, weatherRepository, savedStateHandle,appDataStore)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun onSaveButtonClick_whenLocationTypeIsMap_shouldSetLocation() = runTest {
        // Given
        coEvery { appDataStore.locationType } returns flowOf(LocationType.MAP)
        coEvery { appDataStore.setLocation(any(), any()) } returns Unit
        val events = mutableListOf<UiEvent>()
        val job = launch { viewModel.events.collect { events.add(it) } }

        // When
        viewModel.onSaveButtonClick(30.0, 31.0)
        advanceUntilIdle()

        // Then
        coVerify { appDataStore.setLocation(30.0, 31.0) }
        assert(events.any { it is UiEvent.NavigateBack })
        job.cancel()
    }

    @Test
    fun getAddress_whenSuccess_shouldUpdateLocationDetails() = runTest {
        // Given
        val address = Address("Cairo", "Egypt")
        coEvery { locationRepository.getLocationDetails(any(), any()) } returns Result.success(address)
        var result: Address? = null

        // When
        viewModel.getAddress(30.0, 31.0) { result = it }
        advanceUntilIdle()

        // Then
        assertEquals(address, result)
    }

    @Test
    fun getAddress_whenFailure_shouldEmitShowSnackbar() = runTest {
        // Given
        coEvery { locationRepository.getLocationDetails(any(), any()) } returns Result.failure(Exception())
        val events = mutableListOf<UiEvent>()
        val job = launch { viewModel.events.collect { events.add(it) } }

        // When
        viewModel.getAddress(30.0, 31.0) {}
        advanceUntilIdle()

        // Then
        assert(events.any { it is UiEvent.ShowSnackbar && it.messageId == R.string.something_wrong })
        job.cancel()
    }
}