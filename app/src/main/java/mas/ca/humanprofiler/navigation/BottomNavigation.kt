package mas.ca.humanprofiler.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import mas.ca.humanprofiler.R
import mas.ca.humanprofiler.ui.ThemedText
import mas.ca.humanprofiler.ui.ThemedTextOptions

enum class Routes(val id: String) {
    INPUT("input"),
    HISTORY("history")
}

sealed class BottomNavigationItem(val route: String, val icon: ImageVector, @StringRes val labelResId: Int) {
    data object Input : BottomNavigationItem(Routes.INPUT.id, Icons.Filled.Edit, R.string.input_route_label)
    data object History : BottomNavigationItem(Routes.HISTORY.id, Icons.AutoMirrored.Filled.List, R.string.history_route_label)

    companion object {
        val entries = listOf(Input, History)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentRoute = currentRoute(navController)
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        BottomNavigationItem.entries.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = stringResource(item.labelResId)) },
                label = {
                    ThemedText(
                        modifier = Modifier,
                        text = stringResource(item.labelResId),
                        options = ThemedTextOptions(
                            type = ThemedText.Type.BODY_MEDIUM,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                },
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to prevent building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route)
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true

                    }
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedIndicatorColor = Color.Transparent,
                    disabledIconColor = MaterialTheme.colorScheme.error,
                    disabledTextColor = MaterialTheme.colorScheme.error,
                )
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}