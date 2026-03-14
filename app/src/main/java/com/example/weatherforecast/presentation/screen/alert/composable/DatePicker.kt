package com.example.weatherforecast.presentation.screen.alert.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    initialDate: String,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    val initialMillis = sdf.parse(initialDate)?.time ?: System.currentTimeMillis()

    val state = rememberDatePickerState(initialSelectedDateMillis = initialMillis)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Theme.color.background.card
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DatePicker(
                    state = state,
                    colors = DatePickerDefaults.colors(
                        containerColor = Theme.color.background.card,
                        titleContentColor = Theme.color.text.primary,
                        headlineContentColor = Theme.color.text.primary,
                        weekdayContentColor = Theme.color.text.primary,
                        subheadContentColor = Theme.color.text.primary,
                        navigationContentColor = Theme.color.text.primary,
                        yearContentColor = Theme.color.text.primary,
                        currentYearContentColor = Theme.color.text.primary,
                        selectedYearContentColor = Color.White,
                        selectedYearContainerColor = Theme.color.background.screen,
                        dayContentColor = Theme.color.text.primary,
                        selectedDayContentColor = Color.White,
                        selectedDayContainerColor = Theme.color.background.screen,
                        todayContentColor = Theme.color.text.primary,
                        todayDateBorderColor = Theme.color.background.screen
                    )
                )

                Row(modifier = Modifier.align(Alignment.End)) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(R.string.cancel), color = Theme.color.text.primary)
                    }
                    TextButton(onClick = {
                        state.selectedDateMillis?.let { millis ->
                            val formatted = SimpleDateFormat(
                                "dd/MM/yyyy",
                                Locale.getDefault()
                            ).format(Date(millis))
                            onDateSelected(formatted)
                        }
                        onDismiss()
                    }) {
                        Text(stringResource(R.string.ok), color = Theme.color.text.primary)
                    }
                }
            }
        }
    }
}