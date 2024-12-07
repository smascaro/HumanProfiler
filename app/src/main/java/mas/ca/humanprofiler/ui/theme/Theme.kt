package mas.ca.humanprofiler.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryPostOrange,
    onPrimary = BasicWhite,
    primaryContainer = PrimaryMidOrange,
    onPrimaryContainer = PrimaryPostOrange,
    inversePrimary = NeutralGrey10,
    secondary = BasicDarkGrey,
    onSecondary = DistinctActionRed,
    secondaryContainer = BasicWhite,
    onSecondaryContainer = BasicWhite,
    tertiary = PrimaryPostOrange,
    onTertiary = BasicWhite,
    tertiaryContainer = Color.Transparent,
    onTertiaryContainer = SecondaryContainer,
    background = DarkThemeSurface,
    onBackground = BasicWhite,
    surface = DarkThemeSurface,
    onSurface = BasicWhite,
    surfaceVariant = NeutralGrey10,
    onSurfaceVariant = DarkThemeOnSurface,
    surfaceTint = BasicDarkGrey,
    inverseSurface = BasicWhite,
    inverseOnSurface = BasicDarkGrey,
    error = DistinctActionRed,
    onError = BasicWhite,
    errorContainer = DistinctActionRed,
    onErrorContainer = BasicWhite,
    outline = NeutralGrey20,
    outlineVariant = NeutralGrey60,
    scrim = MiscOrangeTint,
    surfaceBright = PrimaryPostOrange,
    surfaceContainer = DarkThemeSurfaceContainer
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryPostOrange,
    onPrimary = BasicWhite,
    primaryContainer = PrimaryBlue,
    onPrimaryContainer = PrimaryDeepBlue,
    inversePrimary = NeutralGrey10,
    secondary = BasicWhite,
    onSecondary = BasicDarkGrey,
    secondaryContainer = BasicWhite,
    onSecondaryContainer = BasicWhite,
    tertiary = BasicDarkGrey,
    onTertiary = BasicDarkGrey,
    tertiaryContainer = BasicWhite,
    onTertiaryContainer = SecondaryContainer,
    background = BasicWhite,
    onBackground = NeutralGrey20,
    surface = BasicWhite,
    onSurface = BasicDarkGrey,
    surfaceVariant = BasicWhite,
    onSurfaceVariant = DarkThemeOnSurface,
    surfaceTint = BasicWhite,
    inverseSurface = BasicDarkGrey,
    inverseOnSurface = BasicWhite,
    error = DistinctActionRed,
    onError = BasicWhite,
    errorContainer = DistinctActionRed,
    onErrorContainer = BasicWhite,
    outline = NeutralGrey20,
    outlineVariant = NeutralGrey60,
    scrim = MiscScrim,
    surfaceBright = PrimaryPostOrange,
    surfaceContainer = BasicWhite
)

@Composable
fun HumanProfilerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}