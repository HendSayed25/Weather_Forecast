package com.example.weatherforecast.presentation.screen.home.composable


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.designsystem.theme.Theme


@Composable
fun WeatherForecastCard(
    iconPainter: Painter,
    temperature: Int,
    time: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .width(90.dp)
            .height(110.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.TopCenter
    ) {
        Card(
            modifier = modifier
                .fillMaxSize()
                .offset(y = 30.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            border = BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.onBackground),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.background.card)
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "${temperature}°C",
                    style = Theme.textStyle.title.md,
                    color = Theme.color.text.primary,
                    modifier = Modifier.padding(bottom = 2.dp)
                )

                Text(
                    text = time,
                    style = Theme.textStyle.title.sm,
                    color = Theme.color.text.primary,
                )
            }
        }

        Icon(
            painter = iconPainter,
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.offset(y = (10).dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherForecastCardPreview() {
    WeatherForecastCard(
        painterResource(R.drawable.ic_clouds),
        25,
        "25-11"
    )
}