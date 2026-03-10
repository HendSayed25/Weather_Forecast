package com.example.weatherforecast.presentation.favorite.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.presentation.favorite.model.FavoriteItem

@Composable
fun FavoriteCard(
    item: FavoriteItem,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDismiss()
                true
            } else false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(
                        color = Color.Red,
                        shape = RoundedCornerShape(20)
                    ),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(8.dp)
                .background(
                    shape = RoundedCornerShape(20),
                    color = Theme.color.background.card
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.cityName,
                    style = Theme.textStyle.title.md,
                    color = Theme.color.text.primary
                )
                Text(
                    text = item.description,
                    style = Theme.textStyle.title.md,
                    color = Theme.color.text.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    FavoriteCard(
        FavoriteItem(2,"Cairo", "Egypt"),{}
    )
}