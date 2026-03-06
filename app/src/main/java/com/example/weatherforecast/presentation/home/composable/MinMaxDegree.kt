package com.example.weatherforecast.presentation.home.composable


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.designsystem.theme.Theme

@Composable
fun MinMaxDegree(
    maxTemp: Int,
    minTemp: Int,
    isDay: Int,
    modifier: Modifier = Modifier,
    showBackGround: Boolean = true
) {
    val backgroundColor = if (showBackGround) Theme.color.background.screen else Color(0x00000000)

    Box(
        modifier
            .clip(RoundedCornerShape(50.dp))
            .background(backgroundColor)
            .padding(vertical = 8.dp, horizontal = 18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CardContent(
                    painterResource(if (isDay == 1) R.drawable.ic_arrow_top_weather else R.drawable.ic_arrow_top_weather_dark),
                    "$maxTemp °C",
                )
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .width(1.dp)
                    .height(16.dp)
                    .background(Theme.color.text.secondary)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                CardContent(
                    painterResource(if (isDay == 1) R.drawable.ic_arrow_down_weather else R.drawable.ic_arrow_down_weather_dark),
                    "$minTemp °C"
                )
            }
        }
    }
}

@Composable
private fun CardContent(
    icon: Painter,
    text: String
) {
    Image(
        painter = icon,
        contentDescription = "arrow"
    )

    Text(
        text = text,
        style = Theme.textStyle.title.sm,
        modifier = Modifier.padding(start = 4.dp),
        color = Theme.color.text.primary
    )
}

@Preview(showBackground = true)
@Composable
private fun MinMaxPreview() {
    MinMaxDegree(
        20, 15, 1
    )
}