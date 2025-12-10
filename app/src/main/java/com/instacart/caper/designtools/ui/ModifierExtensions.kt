package com.instacart.caper.designtools.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.instacart.caper.designtools.ui.theme.OutlineColor

/**
 * Applies a standard outlined border style used throughout the app.
 *
 * This creates a 2dp border with OutlineColor and 16dp rounded corners.
 */
fun Modifier.outlinedBorder(): Modifier {
    return border(
        width = 2.dp,
        color = OutlineColor,
        shape = RoundedCornerShape(16.dp)
    )
}
