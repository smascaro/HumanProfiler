package mas.ca.humanprofiler.features.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mas.ca.humanprofiler.R
import mas.ca.humanprofiler.di.UiLayerInjection
import mas.ca.humanprofiler.domain.entities.Age
import mas.ca.humanprofiler.domain.entities.Name
import mas.ca.humanprofiler.domain.entities.Profile
import mas.ca.humanprofiler.domain.entities.Sorting
import mas.ca.humanprofiler.ui.ThemedText
import mas.ca.humanprofiler.ui.ThemedTextOptions
import mas.ca.humanprofiler.ui.theme.DefaultIconSize
import mas.ca.humanprofiler.ui.theme.DevicePreviews
import mas.ca.humanprofiler.ui.theme.HumanProfilerTheme
import mas.ca.humanprofiler.ui.theme.ItemHeight
import mas.ca.humanprofiler.ui.theme.PaddingDefault
import mas.ca.humanprofiler.ui.theme.PaddingLarge
import java.util.Date
import kotlin.time.DurationUnit
import kotlin.time.toDuration


data class HistoryScreenState(
    val profiles: List<Profile> = emptyList(),
    val sortedBy: Sorting = Sorting.DEFAULT,
    val showLoadingIndicator: Boolean = false,
    val showSortingChooser: Boolean = false,
    val showError: Boolean = false
)


@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel = viewModel<HistoryViewModel>(factory = UiLayerInjection.INSTANCE!!.provideHistoryViewModelFactory())
    val state by viewModel.uiState.collectAsState(HistoryScreenState())
    HistoryScreen(
        modifier = modifier.fillMaxSize(),
        state = state,
        onNewSortingClick = { viewModel.onSortingChange(it) },
        onCurrentSortingClick = { viewModel.onSortingClick() },
        onSortingSelectorDismissed = { viewModel.onSortingSelectorDismissed() }
    )
}


@Composable
private fun HistoryScreen(
    modifier: Modifier = Modifier,
    state: HistoryScreenState = HistoryScreenState(),
    onNewSortingClick: (Sorting.Type) -> Unit = {},
    onCurrentSortingClick: () -> Unit,
    onSortingSelectorDismissed: () -> Unit
) {
    if (state.showSortingChooser) {
        ProfilesSortingSelectorSheet(
            modifier = Modifier,
            selectedSorting = state.sortedBy,
            onSortingSelected = onNewSortingClick,
            onSheetDismissed = onSortingSelectorDismissed
        )

    }
    when {
        state.showError -> ErrorLoadingText(modifier = modifier)

        else -> HistoryScreenDataContent(
            modifier = modifier,
            state = state,
            onCurrentSortingClick = onCurrentSortingClick
        )
    }
}

@Composable
private fun HistoryScreenDataContent(
    modifier: Modifier,
    state: HistoryScreenState,
    onCurrentSortingClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            SortingRow(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .fillMaxWidth()
                    .padding(PaddingLarge),
                sorting = state.sortedBy,
                onClick = { onCurrentSortingClick() }
            )
        }
        items(state.profiles.size) { index ->
            val profile = state.profiles[index]
            ProfileItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (index % 2 == 0) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onSurfaceVariant)
                    .padding(PaddingLarge),
                profile = profile
            )
        }
    }
}

@Composable
private fun ErrorLoadingText(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        ThemedText(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.something_went_wrong_please_try_again),
            options = ThemedTextOptions(
                style = ThemedText.Style.ERROR
            )
        )
    }
}

@Composable
private fun ProfileItem(
    modifier: Modifier,
    profile: Profile,
) {
    Row(
        modifier = modifier
            .padding(vertical = PaddingDefault)
            .height(ItemHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .weight(1f, false),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ThemedText(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = PaddingDefault),
                text = profile.age.value.toString(),
                options = ThemedTextOptions(
                    textAlignment = TextAlign.Center,
                    type = ThemedText.Type.HEADLINE_MEDIUM
                )
            )
            ThemedText(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(PaddingDefault),
                text = profile.name.value,
                options = ThemedTextOptions(
                    textAlignment = TextAlign.Center,
                    type = ThemedText.Type.HEADLINE_SMALL,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            )
        }
        ThemedText(
            modifier = Modifier,
            text = getLastAccessString(profile.lastAccessed),
            options = ThemedTextOptions(
                textAlignment = TextAlign.Center,
                type = ThemedText.Type.BODY_MEDIUM
            )
        )
    }
}

@Composable
private fun getLastAccessString(date: Date): String {
    val elapsedDuration = (System.currentTimeMillis() - date.time).toDuration(DurationUnit.MILLISECONDS)
    return when {
        elapsedDuration < 60.toDuration(DurationUnit.SECONDS) -> stringResource(R.string.seconds_ago, elapsedDuration.inWholeSeconds)
        elapsedDuration < 60.toDuration(DurationUnit.MINUTES) -> stringResource(R.string.minutes_ago, elapsedDuration.inWholeMinutes)
        elapsedDuration < 24.toDuration(DurationUnit.HOURS) -> stringResource(R.string.hours_ago, elapsedDuration.inWholeHours)
        elapsedDuration < 7.toDuration(DurationUnit.DAYS) -> stringResource(R.string.days_ago, elapsedDuration.inWholeDays)
        elapsedDuration < 28.toDuration(DurationUnit.DAYS) -> stringResource(R.string.weeks_ago, elapsedDuration.inWholeDays / 7) // Approx 4 weeks
        elapsedDuration < 365.toDuration(DurationUnit.DAYS) -> stringResource(R.string.months_ago, elapsedDuration.inWholeDays / 30) // Approx 12 months
        else -> stringResource(R.string.years_ago, elapsedDuration.inWholeDays / 365) // Approx years
    }
}

@Composable
private fun SortingRow(
    modifier: Modifier = Modifier,
    sorting: Sorting,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(ItemHeight)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        ThemedText(
            modifier = Modifier,
            annotatedText = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(stringResource(R.string.sorting_by))
                }
                append(stringResource(id = sorting.type.textResourceId))
            }
        )
        Icon(
            modifier = Modifier
                .size(DefaultIconSize)
                .rotate(-90f),
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = stringResource(R.string.sorting_selection),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileItemPreview() {
    HumanProfilerTheme {
        ProfileItem(
            modifier = Modifier.width(300.dp),
            profile = Profile(Name("Sergi"), Age(29), Date(System.currentTimeMillis().minus(3588000)))
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ErrorMessagePreview() {
    HumanProfilerTheme {
        ErrorLoadingText(
            modifier = Modifier,
        )
    }
}

@DevicePreviews
@Composable
private fun HistoryScreenPreview() {
    HumanProfilerTheme {
        HistoryScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp),
            state = HistoryScreenState(
                profiles = listOf(
                    Profile(Name("Sergi"), Age(29), Date()),
                    Profile(Name("A really long name that should occupy the whole width of virtually any mobile device"), Age(90), Date()),
                    Profile(Name("Anthony"), Age(31), Date()),
                    Profile(Name("Bryan"), Age(44), Date()),
                    Profile(Name("Brian"), Age(60), Date()),
                    Profile(Name("Anthony"), Age(31), Date()),
                    Profile(Name("Bryan"), Age(44), Date()),
                    Profile(Name("Brian"), Age(60), Date()),
                    Profile(Name("Marie"), Age(90), Date()),
                    Profile(Name("Anthony"), Age(31), Date()),
                    Profile(Name("Bryan"), Age(44), Date()),
                    Profile(Name("Brian"), Age(60), Date()),
                    Profile(Name("Marie"), Age(90), Date()),
                ),
                sortedBy = Sorting(Sorting.Type.NAME, Sorting.Direction.ASCENDING),
                showLoadingIndicator = false
            ),
            onNewSortingClick = {},
            onCurrentSortingClick = {},
            onSortingSelectorDismissed = {}
        )

    }
}