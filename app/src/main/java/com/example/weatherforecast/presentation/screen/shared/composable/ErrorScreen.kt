package com.example.weatherforecast.presentation.screen.shared.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.designsystem.theme.Theme

@Composable
fun ErrorScreen(
    message: String, imageId: Int, onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(imageId),
            contentDescription = "image",
            modifier = Modifier.size(72.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = message,
            color = Theme.color.text.primary,
            style = Theme.textStyle.title.md,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRetry, colors = ButtonColors(
                containerColor = Theme.color.background.card,
                contentColor = Theme.color.text.primary,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ), modifier = Modifier.size(width = 180.dp, height = 50.dp)
        ) {
            Text(
                text = stringResource(R.string.retry),
                color = Theme.color.text.primary,
                style = Theme.textStyle.title.md
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ErrorScreen("Error", R.drawable.error) { }
}