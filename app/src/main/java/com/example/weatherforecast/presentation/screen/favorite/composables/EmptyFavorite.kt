package com.example.weatherforecast.presentation.screen.favorite.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.designsystem.theme.Theme

@Composable
fun EmptyFavorite(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.heart),
            contentDescription = "heart image",
            modifier = Modifier.size(72.dp).padding(bottom = 10.dp)
        )

        Text(
            text = stringResource(R.string.no_favorite_yet),
            style = Theme.textStyle.title.md,
            color = Theme.color.text.primary
        )
    }
}

@Preview
@Composable
private fun Preview(){
    EmptyFavorite()
}