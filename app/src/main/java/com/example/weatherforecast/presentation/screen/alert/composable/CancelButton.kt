package com.example.weatherforecast.presentation.screen.alert.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.designsystem.theme.Theme

@Composable
fun CancelButton(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
){
    TextButton(
        onClick = onDismiss,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .background(
                Theme.color.background.card
            )
            .border(
                width = 0.5.dp,
                color = Color.Red,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                Theme.color.background.card,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            text = stringResource(R.string.cancel),
            style = Theme.textStyle.title.md,
            color = Theme.color.text.primary
        )
    }
}