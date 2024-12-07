package mas.ca.humanprofiler.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mas.ca.humanprofiler.features.history.HistoryScreen
import mas.ca.humanprofiler.features.input.InputScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavigationItem.Input.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavigationItem.Input.route) {
                InputScreen()
            }
            composable(BottomNavigationItem.History.route) {
                HistoryScreen()
            }
        }
    }

}

