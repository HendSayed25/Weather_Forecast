package com.example.weatherforecast.presentation.screen.shared.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.designsystem.theme.Theme

@Composable
fun EmptyState(
    @DrawableRes iconId : Int,
    @StringRes textId : Int,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(iconId),
            contentDescription = "image",
            modifier = Modifier.size(72.dp).padding(bottom = 10.dp)
        )

        Text(
            text = stringResource(textId),
            style = Theme.textStyle.title.md,
            color = Theme.color.text.primary
        )
    }
}