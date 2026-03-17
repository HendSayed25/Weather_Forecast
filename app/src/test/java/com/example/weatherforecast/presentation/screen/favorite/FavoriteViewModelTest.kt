package com.example.weatherforecast.presentation.screen.favorite

import android.content.Context
import com.example.weatherforecast.R
import com.example.weatherforecast.data.repository.WeatherRepository
import com.example.weatherforecast.presentation.screen.favorite.model.FavoriteItem
import com.example.weatherforecast.presentation.screen.shared.UiEvent
import com.example.weatherforecast.presentation.screen.shared.UiState
import com.example.weatherforecast.presentation.utils.NetworkUtils
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
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
class FavoriteViewModelTest {

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var context: Context
    private lateinit var viewModel: FavoriteViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        context = mockk(relaxed = true)
        weatherRepository = mockk()

        mockkObject(NetworkUtils)

        coEvery { weatherRepository.getAllFavWeathers() } returns flowOf(emptyList())

        viewModel = FavoriteViewModel(weatherRepository, context)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getFavWeathers_shouldReturnSuccessWithEmptyList() = runTest {
        // Given
        coEvery { weatherRepository.getAllFavWeathers() } returns flowOf(emptyList())

        // When
        viewModel.getFavWeathers()
        advanceUntilIdle()

        // Then
        assertEquals(UiState.Success<List<FavoriteItem>>(emptyList()), viewModel.uiState.value)
    }

    @Test
    fun onAddFavorite_whenNoInternet_shouldEmitShowSnackbar() = runTest {
        // Given
        every { NetworkUtils.isInternetAvailable(context) } returns false
        val events = mutableListOf<UiEvent>()

        val job = launch { viewModel.events.collect { events.add(it) } }

        // When
        viewModel.onAddFavorite()
        advanceUntilIdle()

        // Then
        assert(events.any { it is UiEvent.ShowSnackbar && it.messageId == R.string.no_internet })

        job.cancel()
    }

    @Test
    fun onAddFavorite_whenInternetAvailable_shouldEmitNavigateTo() = runTest {
        // Given
        every { NetworkUtils.isInternetAvailable(context) } returns true
        val events = mutableListOf<UiEvent>()

        val job = launch { viewModel.events.collect { events.add(it) } }

        // When
        viewModel.onAddFavorite()
        advanceUntilIdle()

        // Then
        assert(events.any { it is UiEvent.NavigateTo })

        job.cancel()
    }

    @Test
    fun deleteFromFavorite_shouldCallDeleteWeatherFromFav() = runTest {
        // Given
        coEvery { weatherRepository.deleteWeatherFromFav(any()) } returns Unit

        // When
        viewModel.deleteFromFavorite(1)
        advanceUntilIdle()

        // Then
        coVerify { weatherRepository.deleteWeatherFromFav(1) }
    }

    @Test
    fun deleteFromFavorite_whenException_shouldEmitShowSnackbar() = runTest {
        // Given
        coEvery { weatherRepository.deleteWeatherFromFav(any()) } throws Exception()

        val events = mutableListOf<UiEvent>()

        val job = launch { viewModel.events.collect { events.add(it) } }

        // When
        viewModel.deleteFromFavorite(1)
        advanceUntilIdle()

        // Then
        assert(events.any { it is UiEvent.ShowSnackbar && it.messageId == R.string.something_wrong })
        job.cancel()
    }
}