package com.example.weatherforecast.presentation.screen.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherforecast.R
import com.example.weatherforecast.data.local.datastore.Language
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.presentation.navigation.LocalNavController
import com.example.weatherforecast.presentation.screen.alert.composable.AlertCard
import com.example.weatherforecast.presentation.screen.alert.composable.ReminderDialog
import com.example.weatherforecast.presentation.screen.alert.model.AlertModel
import com.example.weatherforecast.presentation.screen.shared.UiEvent
import com.example.weatherforecast.presentation.screen.shared.UiState
import com.example.weatherforecast.presentation.screen.shared.composable.AppSnackbar
import com.example.weatherforecast.presentation.screen.shared.composable.EmptyState
import com.example.weatherforecast.presentation.screen.shared.composable.FloatingActionButton
import com.example.weatherforecast.presentation.screen.shared.composable.Loading

@Composable
fun AlertScreen(
    viewModel: AlertViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val language by viewModel.language.collectAsStateWithLifecycle()
    val context = LocalNavController.current.context

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(context.getString(event.messageId))
                }

                else -> {}
            }
        }
    }

    when (val uiState = state) {
        is UiState.Loading -> Loading()
        is UiState.Success -> {
            AlertScreenContent(
                alerts = uiState.data,
                language = language,
                snackbarHostState = snackbarHostState,
                onAddAlertClick = viewModel::addAlert,
                onDeleteAlertClick = viewModel::deleteAlert,
                onCancelAlertClick = viewModel::cancelAlert,
                onOpenAlertClick = viewModel::scheduleAlert,
                onEditAlertClick = viewModel::updateAlert
            )
        }

        else -> {}
    }
}

@Composable
private fun AlertScreenContent(
    alerts: List<AlertModel>,
    language: Language,
    snackbarHostState: SnackbarHostState,
    onDeleteAlertClick: (AlertModel) -> Unit,
    onCancelAlertClick: (Int) -> Unit,
    onOpenAlertClick: (AlertModel) -> Unit,
    onAddAlertClick: (String, String, String, String, String, Boolean) -> Unit,
    onEditAlertClick: (AlertModel) -> Unit,
    modifier: Modifier = Modifier
) {

    var showDialog by remember { mutableStateOf(false) }
    var selectedAlert by remember { mutableStateOf<AlertModel?>(null) }



    Box(modifier = modifier.fillMaxSize()) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { showDialog = true })
            },
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.background.screen)
                    .padding(innerPadding)
            ) {

                Text(
                    text = stringResource(R.string.alerts),
                    style = Theme.textStyle.title.lg,
                    color = Theme.color.text.primary,
                    modifier = Modifier.padding(16.dp)
                )

                if (alerts.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        items(
                            count = alerts.size, key = { index -> alerts[index].id }) { index ->
                            AlertCard(
                                alert = alerts[index],
                                onDismiss = { onDeleteAlertClick(alerts[index]) },
                                onToggle = { isEnabled ->
                                    if (isEnabled) onOpenAlertClick(alerts[index])
                                    else onCancelAlertClick(alerts[index].id)
                                    alerts[index].isEnable = isEnabled
                                    onEditAlertClick(alerts[index])
                                },
                                onCardClick = {
                                    selectedAlert = alerts[index]
                                    showDialog = true
                                })
                        }
                    }
                } else {
                    EmptyState(
                        iconId = R.drawable.alert, textId = R.string.no_alerts_yet
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

        if (showDialog) {
            ReminderDialog(alert = selectedAlert, languageCode = language.code, onDismiss = {
                showDialog = false
                selectedAlert = null
            }, onSave = { name, time, date, condition, alertType, isEnable ->
                showDialog = false
                selectedAlert = null
                onAddAlertClick(name, time, date, condition, alertType, isEnable)
            }, onUpdate = {
                showDialog = false
                onEditAlertClick(it)
                selectedAlert = null
            })
        }
    }

}