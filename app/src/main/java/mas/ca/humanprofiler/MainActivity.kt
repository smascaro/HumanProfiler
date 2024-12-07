package mas.ca.humanprofiler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mas.ca.humanprofiler.navigation.AppNavigation
import mas.ca.humanprofiler.ui.theme.HumanProfilerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HumanProfilerTheme {
                AppNavigation()
            }
        }
    }
}