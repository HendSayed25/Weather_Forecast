package com.example.weatherforecast.presentation.screen.alert.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.weatherforecast.R
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.presentation.screen.alert.model.AlertModel
import com.example.weatherforecast.presentation.utils.TimeUtils.formatTimeForLanguage
import com.example.weatherforecast.presentation.utils.TimeUtils.normalizeTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderDialog(
    alert: AlertModel?,
    languageCode: String,
    onDismiss: () -> Unit,
    onSave: (name: String, time: String, date: String, condition: String, alertType: String, isEnable: Boolean) -> Unit,
    onUpdate: (AlertModel) -> Unit
) {
    var reminderName by remember { mutableStateOf(alert?.title ?: "") }
    val context = LocalContext.current
    var time by remember { mutableStateOf(alert?.formattedTime ?: SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())) }
    var date by remember { mutableStateOf(alert?.formattedDate ?: SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())) }
    var alertType by remember { mutableStateOf(alert?.type ?: "alarm") }
    var selectedReason by remember { mutableStateOf(alert?.condition?.ifEmpty { context.getString(R.string.condition_select_reason) } ?: context.getString(R.string.condition_select_reason)) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Theme.color.background.card,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = stringResource(R.string.alert_settings),
                    style = Theme.textStyle.title.md,
                    color = Theme.color.text.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = stringResource(R.string.alert_name),
                    style = Theme.textStyle.title.md,
                    color = Theme.color.text.primary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                OutlinedTextField(
                    value = reminderName,
                    onValueChange = {
                        reminderName = it
                        nameError = false
                    },
                    isError = nameError,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Theme.color.background.screen,
                        unfocusedBorderColor = Theme.color.text.primary.copy(alpha = 0.5f),
                        cursorColor = Theme.color.background.screen,
                        focusedLabelColor = Theme.color.background.screen,
                        unfocusedLabelColor = Theme.color.text.primary,
                        focusedTextColor = Theme.color.text.primary,
                        unfocusedTextColor = Theme.color.text.primary,
                    ),
                    placeholder = {
                        Text(
                            stringResource(R.string.name_example),
                            style = Theme.textStyle.title.sm,
                            color = Theme.color.text.primary
                        )
                    },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                Text(
                    text = stringResource(R.string.time),
                    style = Theme.textStyle.title.md,
                    color = Theme.color.text.primary,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Row {
                    CustomTextField(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Edit,
                        value = time,
                        onIconClick = { showTimePicker = true },
                        onValueChange = { time = it }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    CustomTextField(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.DateRange,
                        value = date,
                        onIconClick = { showDatePicker = true },
                        onValueChange = { date = it }
                    )
                }

                Text(
                    text = stringResource(R.string.alert_type),
                    style = Theme.textStyle.title.md,
                    color = Theme.color.text.primary,
                    modifier = Modifier.padding(top = 16.dp)
                )

                AlertTypeSection(
                    alertType = alertType,
                    onAlertTypeChange = { alertType = it }
                )

                Text(
                    text = stringResource(R.string.custom_condition),
                    style = Theme.textStyle.title.md,
                    color = Theme.color.text.primary,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )

                ConditionDropdown(
                    selectedCondition = selectedReason,
                    onConditionSelected = { selectedReason = it }
                )

                Spacer(Modifier.height(16.dp))

                SaveButton(
                    alert = alert,
                    reminderName = reminderName,
                    time = time,
                    date = date,
                    selectedReason = if(selectedReason == stringResource(R.string.condition_select_reason)) "" else selectedReason,
                    alertType = alertType,
                    onSave = onSave,
                    onUpdate = onUpdate,
                    languageCode = languageCode,
                    onErrorFound = { nameError = it }
                )

                CancelButton(
                    onDismiss = onDismiss,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                if (showTimePicker) {
                    CustomTimePicker(
                        initialTime = normalizeTime(time),
                        onTimeSelected = {
                            time = formatTimeForLanguage(it, languageCode)
                        },
                        onDismiss = {
                            showTimePicker = false
                        }
                    )
                }

                if (showDatePicker) {
                    CustomDatePicker(
                        initialDate = date,
                        onDateSelected = { date = it },
                        onDismiss = { showDatePicker = false }
                    )
                }
            }
        }
    }
}