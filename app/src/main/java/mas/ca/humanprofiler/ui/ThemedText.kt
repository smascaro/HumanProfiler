package mas.ca.humanprofiler.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

object ThemedText {
    enum class Type {
        HEADLINE_MEDIUM,
        HEADLINE_SMALL,
        BODY_LARGE,
        BODY_MEDIUM,
        BODY_SMALL
    }
}

@Composable
fun ThemedText(
    modifier: Modifier = Modifier,
    annotatedText: AnnotatedString,
    options: ThemedTextOptions = ThemedTextOptions()
) {
    Text(
        modifier = modifier,
        text = annotatedText,
        textAlign = options.textAlignment,
        style = when (options.type) {
            ThemedText.Type.HEADLINE_MEDIUM -> MaterialTheme.typography.headlineMedium
            ThemedText.Type.HEADLINE_SMALL -> MaterialTheme.typography.headlineSmall
            ThemedText.Type.BODY_LARGE -> MaterialTheme.typography.bodyLarge
            ThemedText.Type.BODY_MEDIUM -> MaterialTheme.typography.bodyMedium
            ThemedText.Type.BODY_SMALL -> MaterialTheme.typography.bodySmall
        }.let { style ->
            options.fontWeight?.let { fontWeight -> style.copy(fontWeight = fontWeight) } ?: style
        }.let { style ->
            options.textColor?.let { color -> style.copy(color = color) } ?: style
        },
        overflow = options.overflow ?: TextOverflow.Visible,
        maxLines = options.maxLines ?: Int.MAX_VALUE
    )
}

@Composable
fun ThemedText(
    modifier: Modifier = Modifier,
    text: String,
    options: ThemedTextOptions = ThemedTextOptions()
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = options.textAlignment,
        style = when (options.type) {
            ThemedText.Type.HEADLINE_MEDIUM -> MaterialTheme.typography.headlineMedium
            ThemedText.Type.HEADLINE_SMALL -> MaterialTheme.typography.headlineSmall
            ThemedText.Type.BODY_LARGE -> MaterialTheme.typography.bodyLarge
            ThemedText.Type.BODY_MEDIUM -> MaterialTheme.typography.bodyMedium
            ThemedText.Type.BODY_SMALL -> MaterialTheme.typography.bodySmall
        }.let { style ->
            options.fontWeight?.let { fontWeight -> style.copy(fontWeight = fontWeight) } ?: style
        }.let { style ->
            options.textColor?.let { color -> style.copy(color = color) } ?: style
        },
        overflow = options.overflow ?: TextOverflow.Visible,
        maxLines = options.maxLines ?: Int.MAX_VALUE
    )
}

data class ThemedTextOptions(
    val textAlignment: TextAlign? = null,
    val fontWeight: FontWeight? = null,
    val type: ThemedText.Type = ThemedText.Type.BODY_LARGE,
    val overflow: TextOverflow? = null,
    val maxLines: Int? = null,
    val textColor: Color? = null
)