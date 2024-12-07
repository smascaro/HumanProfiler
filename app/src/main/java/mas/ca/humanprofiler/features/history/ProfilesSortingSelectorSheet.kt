package mas.ca.humanprofiler.features.history

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import mas.ca.humanprofiler.R
import mas.ca.humanprofiler.domain.entities.Sorting
import mas.ca.humanprofiler.ui.BottomSheet
import mas.ca.humanprofiler.ui.theme.DefaultIconSize
import mas.ca.humanprofiler.ui.theme.HumanProfilerTheme
import mas.ca.humanprofiler.ui.theme.ItemHeight
import mas.ca.humanprofiler.ui.theme.PaddingLarge

@Composable
fun ProfilesSortingSelectorSheet(
    modifier: Modifier = Modifier,
    selectedSorting: Sorting,
    onSortingSelected: (Sorting.Type) -> Unit,
    onSheetDismissed: () -> Unit = { }
) {
    BottomSheet(
        titleResource = R.string.choose_sorting_mode,
        onSheetDismissed = onSheetDismissed
    ) {
        ProfilesSortingSelectorSheetContent(
            modifier = modifier,
            selectedSorting = selectedSorting,
            onSortingSelected = onSortingSelected,
        )
    }
}

@Composable
private fun ProfilesSortingSelectorSheetContent(
    modifier: Modifier,
    selectedSorting: Sorting,
    onSortingSelected: (Sorting.Type) -> Unit,
) {
    Sorting.Type.entries.forEach { type ->
        val selected = selectedSorting.type == type
        SortingOption(
            modifier = modifier
                .height(ItemHeight)
                .padding(PaddingLarge),
            type = type,
            direction = selectedSorting.direction,
            selected = selected,
            onClick = { onSortingSelected(type) }
        )
    }

}

@Composable
private fun SortingOption(
    modifier: Modifier = Modifier,
    type: Sorting.Type,
    direction: Sorting.Direction,
    selected: Boolean,
    onClick: (Sorting.Type) -> Unit
) {
    val rotationAnimation by animateFloatAsState(
        targetValue = if (direction == Sorting.Direction.ASCENDING) 90f else -90f,
        label = "Selected sorting order"
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(type) },
        horizontalArrangement = if (selected) Arrangement.SpaceBetween else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = type.textResourceId)
        )
        if (selected) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                modifier = Modifier
                    .size(DefaultIconSize)
                    .graphicsLayer(rotationZ = rotationAnimation),
                contentDescription = stringResource(R.string.sorting_order)
            )
        }
    }
}

val Sorting.Type.textResourceId: Int
    get() {
        return when (this) {
            Sorting.Type.NAME -> R.string.sorting_mode_alphabetical
            Sorting.Type.AGE -> R.string.sorting_mode_age
            Sorting.Type.LAST_ACCESSED -> R.string.sorting_mode_last_access
        }
    }

@Preview(showBackground = true)
@Composable
private fun SortingOptionPreview() {
    HumanProfilerTheme {
        SortingOption(
            type = Sorting.Type.NAME,
            direction = Sorting.Direction.ASCENDING,
            selected = true,
            onClick = { }
        )
    }
}