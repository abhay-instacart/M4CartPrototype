package com.instacart.caper.designtools.ui

import androidx.compose.ui.tooling.preview.Preview

/**
 * Custom preview annotation for Fire 10 tablet screens.
 *
 * Displays the composable in landscape orientation at 1920x1200 resolution with 224 DPI.
 * This is the standard preview configuration for Fire 10 tablet screens.
 */
@Preview(
    name = "Fire 10 Landscape Preview",
    device = "spec:width=1920px,height=1200px,dpi=240,orientation=landscape",
    showBackground = true
)
annotation class Fire10Preview

/**
 * Custom preview annotation for Fire 11 tablet screens.
 *
 * Displays the composable in landscape orientation at 2000x1200 resolution with 213 DPI.
 * This is the standard preview configuration for Fire 11 tablet screens.
 */
@Preview(
    name = "Fire 11 Landscape Preview",
    device = "spec:width=2000px,height=1200px,dpi=240,orientation=landscape",
    showBackground = true
)
annotation class Fire11Preview

/**
 * Custom preview annotation for home screen sections.
 *
 * Displays the composable in landscape orientation at 1112x652 DP with 240 DPI.
 * This preview configuration is designed for section components that appear in the
 * main content area of the home screen (between the navigation bar and mini cart).
 */
@Preview(
    name = "Home Section Preview",
    device = "spec:width=1112dp,height=652dp,dpi=240, orientation=landscape"
)
annotation class SectionPreview