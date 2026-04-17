package com.example.weatherforecast.presentation.screen.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherforecast.R
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.presentation.navigation.LocalNavController
import com.example.weatherforecast.presentation.navigation.Route
import com.example.weatherforecast.presentation.screen.favorite.composables.FavoriteCard
import com.example.weatherforecast.presentation.screen.favorite.model.FavoriteItem
import com.example.weatherforecast.presentation.screen.shared.UiEvent
import com.example.weatherforecast.presentation.screen.shared.UiState
import com.example.weatherforecast.presentation.screen.shared.composable.AppSnackbar
import com.example.weatherforecast.presentation.screen.shared.composable.EmptyState
import com.example.weatherforecast.presentation.screen.shared.composable.FloatingActionButton
import com.example.weatherforecast.presentation.screen.shared.composable.Loading

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = LocalNavController.current
    val context = LocalNavController.current.context


    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(context.getString(event.messageId))
                }

                is UiEvent.NavigateTo -> {
                    navController.navigate(Route.MapRoute(false))
                }

                else -> {}
            }
        }
    }

    when (val state = uiState) {
        is UiState.Loading -> {
            Loading()
        }

        is UiState.Success -> {
            FavoriteScreenContent(
                modifier = Modifier,
                favorites = state.data,
                snackbarHostState = snackbarHostState,
                onDeleteItem = viewModel::deleteFromFavorite,
                onAddItemToFav = viewModel::onAddFavorite,
                onFavItemClick = { id -> navController.navigate(Route.HomeRoute(id)) })
        }

        else -> {}
    }
}

@Composable
private fun FavoriteScreenContent(
    favorites: List<FavoriteItem>,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onDeleteItem: (Int) -> Unit,
    onAddItemToFav: () -> Unit,
    onFavItemClick: (Int) -> Unit
) {

    Box(modifier = modifier.fillMaxSize()) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { onAddItemToFav() })
            },
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.background.screen)
                    .padding(innerPadding)
            ) {

                Text(
                    text = stringResource(R.string.favorites),
                    style = Theme.textStyle.title.lg,
                    color = Theme.color.text.primary,
                    modifier = Modifier.padding(16.dp),
                )

                if (favorites.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        items(items = favorites, key = { it.id }) { item ->
                            FavoriteCard(
                                item = item,
                                onDismiss = { onDeleteItem(item.id) },
                                modifier = Modifier.clickable { onFavItemClick(item.id) })
                        }
                    }
                } else {
                    EmptyState(
                        iconId = R.drawable.heart, textId = R.string.no_favorite_yet
                    )
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
            snackbar = { snackbarData ->
                AppSnackbar(
                    message = snackbarData.visuals.message
                )
            })
    }
}