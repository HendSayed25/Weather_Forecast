package com.example.weatherforecast.presentation.screen.alert.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.presentation.utils.AlertType

@Composable
fun AlertTypeSection(
    alertType: String,
    onAlertTypeChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        RadioButton(
            selected = alertType.uppercase() == AlertType.ALARM.name,
            onClick = { onAlertTypeChange(AlertType.ALARM.name) },
            colors = RadioButtonDefaults.colors(
                selectedColor = Theme.color.background.screen,
                unselectedColor = Color.White
            )
        )

        Text(
            text = stringResource(R.string.alarm),
            style = Theme.textStyle.title.md,
            color = Theme.color.text.primary,
        )

        Spacer(Modifier.width(20.dp))

        RadioButton(
            selected = alertType.uppercase() == AlertType.NOTIFICATION.name,
            onClick = { onAlertTypeChange(AlertType.NOTIFICATION.name) },
            colors = RadioButtonDefaults.colors(
                selectedColor = Theme.color.background.screen,
                unselectedColor = Color.White
            )
        )

        Text(
            text = stringResource(R.string.notification),
            style = Theme.textStyle.title.md,
            color = Theme.color.text.primary
        )
    }
}