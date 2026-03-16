package com.example.weatherforecast.presentation.screen.alert.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.presentation.screen.alert.model.AlertModel
import com.example.weatherforecast.presentation.utils.TimeUtils

@Composable
fun SaveButton(
    alert: AlertModel?,
    reminderName: String,
    time: String,
    date: String,
    languageCode: String,
    selectedReason: String,
    alertType: String,
    onErrorFound: (Boolean) -> Unit,
    onSave: (name: String, time: String, date: String, condition: String, alertType: String, isEnable: Boolean) -> Unit,
    onUpdate: (AlertModel) -> Unit,
) {
    val selectReason = stringResource(R.string.condition_select_reason)

    Button(
        onClick = {
            if (reminderName.isBlank()) {
                onErrorFound(true)
                return@Button
            }
            onErrorFound(false)

            val inMillis = TimeUtils.convertToMillis(date, time, languageCode)
            val finalCondition = if (selectedReason == selectReason) "" else selectedReason

            if (alert == null) onSave(
                reminderName, time, date, finalCondition, alertType, true

            )
            else onUpdate(
                AlertModel(
                    id = alert.id,
                    title = reminderName,
                    type = alertType.lowercase(),
                    dateInMillis = inMillis,
                    timeInMillis = inMillis,
                    formattedTime = "",
                    formattedDate = "",
                    condition = finalCondition,
                    isEnable = alert.isEnable
                )
            )
        },
        colors = ButtonColors(
            containerColor = Theme.color.background.screen,
            contentColor = Theme.color.text.primary,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(bottom = 8.dp)
            .background(Theme.color.background.card),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = stringResource(R.string.save),
            style = Theme.textStyle.title.md,
            color = Theme.color.text.primary
        )
    }
}
