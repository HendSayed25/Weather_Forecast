package com.example.weatherforecast.presentation.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.designsystem.theme.Theme

@Composable
fun AppSnackbar(
    message: String,
    modifier: Modifier = Modifier,
) {
    Snackbar(
        modifier = modifier.padding(16.dp),
        containerColor = Theme.color.background.card
    ) {
        Text(text = message, style = Theme.textStyle.title.sm , color = Theme.color.text.primary)
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview(){
    AppSnackbar("Success")
}