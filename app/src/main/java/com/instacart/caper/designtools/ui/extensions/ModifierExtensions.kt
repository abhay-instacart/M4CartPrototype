package com.instacart.caper.designtools.ui.extensions

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Extension function to add fading edges to scrollable content (for verticalScroll).
 * Shows a fade at the top when content can be scrolled up,
 * and a fade at the bottom when content can be scrolled down.
 *
 * @param scrollState The ScrollState to monitor scroll position
 * @param fadeLength The length of the fade effect in dp
 * @param fadeColor The color to fade to (usually matches background)
 */
fun Modifier.fadingEdges(
    scrollState: ScrollState,
    fadeLength: Dp = 24.dp,
    fadeColor: Color = Color.White
): Modifier = this.then(
    Modifier.drawWithContent {
        drawContent()

        val fadeLengthPx = fadeLength.toPx()

        // Can scroll up (not at top)
        val canScrollUp = scrollState.value > 0

        // Can scroll down (not at bottom)
        val canScrollDown = scrollState.value < scrollState.maxValue

        // Draw top fade if we can scroll up
        if (canScrollUp) {
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        fadeColor,
                        Color.Transparent
                    ),
                    startY = 0f,
                    endY = fadeLengthPx
                ),
                topLeft = Offset.Zero,
                size = Size(size.width, fadeLengthPx)
            )
        }

        // Draw bottom fade if we can scroll down
        if (canScrollDown) {
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        fadeColor
                    ),
                    startY = size.height - fadeLengthPx,
                    endY = size.height
                ),
                topLeft = Offset(0f, size.height - fadeLengthPx),
                size = Size(size.width, fadeLengthPx)
            )
        }
    }
)

/**
 * Extension function to add fading edges to lazy list content (for LazyColumn/LazyVerticalGrid).
 * Shows a fade at the top when content can be scrolled up,
 * and a fade at the bottom when content can be scrolled down.
 *
 * @param lazyListState The LazyListState to monitor scroll position
 * @param fadeLength The length of the fade effect in dp
 * @param fadeColor The color to fade to (usually matches background)
 */
fun Modifier.fadingEdges(
    lazyListState: LazyListState,
    fadeLength: Dp = 32.dp,
    fadeColor: Color = Color.White
): Modifier = this.then(
    Modifier.drawWithContent {
        drawContent()

        val fadeLengthPx = fadeLength.toPx()

        // Can scroll up (not at top)
        val canScrollUp = lazyListState.firstVisibleItemIndex > 0 ||
                lazyListState.firstVisibleItemScrollOffset > 0

        // Can scroll down (not at bottom)
        val layoutInfo = lazyListState.layoutInfo
        val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
        val canScrollDown = lastVisibleItem?.let {
            it.index < layoutInfo.totalItemsCount - 1 ||
                    it.offset + it.size > layoutInfo.viewportEndOffset
        } ?: false

        // Draw top fade if we can scroll up
        if (canScrollUp) {
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        fadeColor,
                        Color.Transparent
                    ),
                    startY = 0f,
                    endY = fadeLengthPx
                ),
                topLeft = Offset.Zero,
                size = Size(size.width, fadeLengthPx)
            )
        }

        // Draw bottom fade if we can scroll down
        if (canScrollDown) {
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        fadeColor
                    ),
                    startY = size.height - fadeLengthPx,
                    endY = size.height
                ),
                topLeft = Offset(0f, size.height - fadeLengthPx),
                size = Size(size.width, fadeLengthPx)
            )
        }
    }
)

/**
 * Extension function to add fading edges to lazy grid content (for LazyVerticalGrid).
 * Shows a fade at the top when content can be scrolled up,
 * and a fade at the bottom when content can be scrolled down.
 *
 * @param lazyGridState The LazyGridState to monitor scroll position
 * @param fadeLength The length of the fade effect in dp
 * @param fadeColor The color to fade to (usually matches background)
 */
fun Modifier.fadingEdges(
    lazyGridState: LazyGridState,
    fadeLength: Dp = 32.dp,
    fadeColor: Color = Color.White
): Modifier = this.then(
    Modifier.drawWithContent {
        drawContent()

        val fadeLengthPx = fadeLength.toPx()

        // Can scroll up (not at top)
        val canScrollUp = lazyGridState.firstVisibleItemIndex > 0 ||
                lazyGridState.firstVisibleItemScrollOffset > 0

        // Can scroll down (not at bottom)
        val layoutInfo = lazyGridState.layoutInfo
        val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
        val canScrollDown = lastVisibleItem?.let {
            it.index < layoutInfo.totalItemsCount - 1 ||
                    it.offset.y + it.size.height > layoutInfo.viewportEndOffset
        } ?: false

        // Draw top fade if we can scroll up
        if (canScrollUp) {
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        fadeColor,
                        Color.Transparent
                    ),
                    startY = 0f,
                    endY = fadeLengthPx
                ),
                topLeft = Offset.Zero,
                size = Size(size.width, fadeLengthPx)
            )
        }

        // Draw bottom fade if we can scroll down
        if (canScrollDown) {
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        fadeColor
                    ),
                    startY = size.height - fadeLengthPx,
                    endY = size.height
                ),
                topLeft = Offset(0f, size.height - fadeLengthPx),
                size = Size(size.width, fadeLengthPx)
            )
        }
    }
)
