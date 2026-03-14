package com.example.weatherforecast.presentation.screen.alert.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.weatherforecast.R
import com.example.weatherforecast.designsystem.theme.Theme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePicker(
    initialTime : String,
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {

    val sdf = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    val date = sdf.parse(initialTime) ?: Date()
    val calendar = Calendar.getInstance().apply { time = date }

    val state = rememberTimePickerState(
        initialHour = calendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = calendar.get(Calendar.MINUTE),
        is24Hour = false
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Theme.color.background.card
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimeInput(
                    state = state,
                    colors = TimePickerDefaults.colors(
                        clockDialColor = Theme.color.background.screen,
                        clockDialSelectedContentColor = Color.White,
                        clockDialUnselectedContentColor = Theme.color.text.primary,
                        selectorColor = Theme.color.background.screen,
                        containerColor = Theme.color.background.card,
                        periodSelectorBorderColor = Theme.color.background.screen,
                        timeSelectorSelectedContainerColor = Theme.color.background.screen,
                        timeSelectorUnselectedContainerColor = Theme.color.background.screen.copy(alpha = 0.3f),
                        timeSelectorSelectedContentColor = Color.White,
                        timeSelectorUnselectedContentColor = Theme.color.text.primary
                    )
                )

                Row(
                    modifier = Modifier.align(Alignment.End)
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(R.string.cancel), color = Theme.color.text.primary)
                    }
                    TextButton(onClick = {
                        val hour12 = when {
                            state.hour == 0 -> 12
                            state.hour > 12 -> state.hour - 12
                            else -> state.hour
                        }
                        val amPm = if (state.hour < 12) "AM" else "PM"
                        val formatted = String.format("%02d:%02d %s", hour12, state.minute, amPm)
                        onTimeSelected(formatted)
                        onDismiss()
                    }) {
                        Text(stringResource(R.string.ok), color = Theme.color.text.primary)
                    }
                }
            }
        }
    }
}