package com.example.weatherforecast.presentation.favorite.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddToFavBtn(
    onClick : ()->Unit,
    modifier: Modifier = Modifier
){

    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = Color(0xFFFFFFFF),
        shape = RoundedCornerShape(50)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add to Favorite",
            tint = Color(0xFF000000)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AddToFavBtn({})
}