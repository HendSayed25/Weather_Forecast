import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.presentation.navigation.bottomNavItems

@Composable
fun BottomNavBar(navController: NavController) {

    // Observe the current back stack entry as a state, so the composable recomposes whenever the screen changes
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar (
        containerColor = Theme.color.background.card,
        modifier = Modifier.padding(0.dp)
    ){
        bottomNavItems.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any { dest ->
                dest.route?.contains(item.route::class.qualifiedName ?: "") == true
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop back stack up to the start destination to avoid building up a large stack of destinations
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true      // Avoid multiple copies of the same destination
                        restoreState = true         // Restore state when navigating back to a previously visited tab
                    }
                },
                icon = { Icon(item.icon, contentDescription = stringResource(item.label)) },
                label = { Text(stringResource(item.label)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Theme.color.text.primary,
                    unselectedIconColor = Theme.color.text.primary.copy(alpha = 0.6f),
                    selectedTextColor = Theme.color.text.secondary,
                    unselectedTextColor = Theme.color.text.primary.copy(alpha = 0.6f),
                    indicatorColor =Theme.color.text.primary.copy(alpha = 0.2f)
                )
            )
        }
    }
}
