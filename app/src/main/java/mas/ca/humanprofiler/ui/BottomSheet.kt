package mas.ca.humanprofiler.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import mas.ca.humanprofiler.R
import mas.ca.humanprofiler.ui.theme.DevicePreviews
import mas.ca.humanprofiler.ui.theme.HumanProfilerTheme
import mas.ca.humanprofiler.ui.theme.MarginLarge
import mas.ca.humanprofiler.ui.theme.PaddingDefault


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    @StringRes titleResource: Int,
    onSheetDismissed: () -> Unit = { },
    content: @Composable ColumnScope.() -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState(true)
    var show by remember { mutableStateOf(true) }

    if (show) {
        ModalBottomSheet(
            onDismissRequest = {
                show = false
                onSheetDismissed()
            },
            modifier = Modifier.wrapContentHeight(),
            sheetState = modalBottomSheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            BottomSheetContent(titleResource, content)
        }
    }
}

@Composable
private fun BottomSheetContent(
    @StringRes titleResource: Int,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = PaddingDefault)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = titleResource),
        )
        Spacer(modifier = Modifier.height(MarginLarge))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(MarginLarge))
        content(this)
    }
}

/**
 * IMPORTANT:
 * In order to test the previews and see the bottom sheet, you need to enable the `Interactive Mode`
 * or the previews will remain blank.
 */
@Preview(widthDp = 1200, heightDp = 300, backgroundColor = 0xFF0000, showBackground = true)
@Composable
fun EnableInteractiveModeReminder() {
    Column(
        modifier = Modifier.padding(horizontal = PaddingDefault),
        verticalArrangement = Arrangement.Center
    ) {
        val spannedText = buildAnnotatedString {
            append("Enable ")
            withStyle(SpanStyle(fontWeight = FontWeight.Black, textDecoration = TextDecoration.Underline)) {
                append("Interactive Mode")
            }
            append(" in a Preview in order to see the actual bottom sheet")
        }
        Text(
            text = spannedText,
            fontSize = TextUnit(56f, TextUnitType.Sp),
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@DevicePreviews
@Composable
fun BottomSheetModalPreview() {
    HumanProfilerTheme {
        BottomSheet(titleResource = R.string.choose_sorting_mode) {
            repeat(30) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp),
                    text = "Hello world! #$it",
                )
            }
        }
    }
}
