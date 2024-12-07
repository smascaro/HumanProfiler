package mas.ca.humanprofiler.ui.theme

import androidx.compose.ui.tooling.preview.Preview

@PhoneDayNightPreviews
@TabletDayNightPreviews
annotation class DevicePreviews

@Preview(showSystemUi = true, device = "spec:parent=pixel_5,orientation=portrait", name = "Normal Phone - Portrait (Day)")
@Preview(showSystemUi = true, device = "spec:parent=pixel_5,orientation=portrait", name = "Normal Phone - Portrait (Night)", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true, device = "spec:parent=pixel_5,orientation=landscape", name = "Normal Phone - Landscape (Day)")
@Preview(showSystemUi = true, device = "spec:parent=pixel_5,orientation=landscape", name = "Normal Phone - Landscape (Night)", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
annotation class PhoneDayNightPreviews

@Preview(showSystemUi = true, device = "spec:parent=pixel_c,orientation=portrait", name = "Tablet - Portrait (Day)")
@Preview(showSystemUi = true, device = "spec:parent=pixel_c,orientation=portrait", name = "Tablet - Portrait (Night)", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true, device = "spec:parent=pixel_c,orientation=landscape", name = "Tablet - Landscape (Day)")
@Preview(showSystemUi = true, device = "spec:parent=pixel_c,orientation=landscape", name = "Tablet - Landscape (Night)", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
annotation class TabletDayNightPreviews
