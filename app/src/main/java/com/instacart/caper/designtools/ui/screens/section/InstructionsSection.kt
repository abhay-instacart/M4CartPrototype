package com.instacart.caper.designtools.ui.screens.section

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.instacart.caper.designtools.R
import com.instacart.caper.designtools.ui.SectionPreview
import com.instacart.caper.designtools.ui.theme.CheckoutGreen
import kotlinx.coroutines.delay

private const val STEP_DURATION_MS = 3500L

data class InstructionStep(
    val number: Int,
    val title: String,
    val imageResId: Int
)

@Composable
fun InstructionsSection(
    onClose: () -> Unit = {}
) {
    val steps = listOf(
        InstructionStep(1, "Tap \"Add item\"", R.drawable.step_1_image),
        InstructionStep(2, "Select your item", R.drawable.step_2_image),
        InstructionStep(3, "Place in cart to weigh", R.drawable.step_3_image),
        InstructionStep(4, "Tap \"Confirm\" to add to cart", R.drawable.step_4_image)
    )

    var currentStepIndex by remember { mutableIntStateOf(0) }
    var isPlaying by remember { mutableStateOf(true) }
    var currentStepProgress by remember { mutableFloatStateOf(0f) }
    var elapsedTimeWhenPaused by remember { mutableLongStateOf(0L) }

    // Auto-progress through steps when playing (but stop at the last step)
    LaunchedEffect(currentStepIndex, isPlaying) {
        if (isPlaying && currentStepIndex < steps.size - 1) {
            // Animate progress from current position to 1 over remaining time
            val duration = STEP_DURATION_MS
            val startTime = System.currentTimeMillis() - elapsedTimeWhenPaused

            while (System.currentTimeMillis() - startTime < duration) {
                val elapsed = System.currentTimeMillis() - startTime
                currentStepProgress = elapsed / duration.toFloat()
                elapsedTimeWhenPaused = elapsed
                delay(16) // ~60 fps
            }

            currentStepProgress = 1f
            currentStepIndex += 1
            currentStepProgress = 0f
            elapsedTimeWhenPaused = 0L
        } else if (isPlaying && currentStepIndex == steps.size - 1) {
            // Animate progress on the last step, then stop
            val duration = STEP_DURATION_MS
            val startTime = System.currentTimeMillis() - elapsedTimeWhenPaused

            while (System.currentTimeMillis() - startTime < duration) {
                val elapsed = System.currentTimeMillis() - startTime
                currentStepProgress = elapsed / duration.toFloat()
                elapsedTimeWhenPaused = elapsed
                delay(16) // ~60 fps
            }

            currentStepProgress = 1f
            elapsedTimeWhenPaused = duration
            isPlaying = false
        }
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, bottom = 40.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 32.dp)
        ) {
            // Top bar with play/pause, progress indicators, and close button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Play/Pause button
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.Black)
                        .clickable {
                            if (isPlaying) {
                                // User manually paused - keep progress and elapsed time
                                isPlaying = false
                            } else {
                                // User pressed play
                                if (currentStepIndex == steps.size - 1 && currentStepProgress >= 1f) {
                                    // At the end, restart from beginning
                                    currentStepIndex = 0
                                    currentStepProgress = 0f
                                    elapsedTimeWhenPaused = 0L
                                }
                                isPlaying = true
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        targetState = isPlaying,
                        transitionSpec = {
                            (fadeIn(animationSpec = tween(300)) + scaleIn(
                                initialScale = 0.8f,
                                animationSpec = tween(300)
                            )).togetherWith(
                                fadeOut(animationSpec = tween(300)) + scaleOut(
                                    targetScale = 0.8f,
                                    animationSpec = tween(300)
                                )
                            )
                        },
                        label = "Play/Pause animation"
                    ) { playing ->
                        Icon(
                            imageVector = if (playing) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (playing) "Pause" else "Play",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                // Progress indicators
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 136.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    steps.forEachIndexed { index, _ ->
                        ProgressIndicator(
                            modifier = Modifier.weight(1f),
                            stepIndex = index,
                            currentStepIndex = currentStepIndex,
                            currentStepProgress = currentStepProgress
                        )
                    }
                }

                // Close button
                IconButton(
                    onClick = onClose,
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Black,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "How to add non-barcode items",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Main content: Steps list and image
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 80.dp)
                    .padding(bottom = 64.dp),
            ) {
                // Left side: Steps list
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    steps.forEachIndexed { index, step ->
                        StepItem(
                            step = step,
                            isActive = index == currentStepIndex
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Right side: Step image
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Crossfade(
                        targetState = currentStepIndex,
                        animationSpec = tween(durationMillis = 300),
                        label = "Step image transition"
                    ) { stepIndex ->
                        Image(
                            painter = painterResource(id = steps[stepIndex].imageResId),
                            contentDescription = "Step ${stepIndex + 1} illustration",
                            modifier = Modifier.fillMaxHeight(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    stepIndex: Int,
    currentStepIndex: Int,
    currentStepProgress: Float
) {
    // Determine the fill progress for this indicator
    val progress = when {
        stepIndex < currentStepIndex -> 1f // Completed step
        stepIndex == currentStepIndex -> currentStepProgress // Current step (animating)
        else -> 0f // Future step
    }

    Box(
        modifier = modifier
            .height(12.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color.LightGray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .background(CheckoutGreen)
        )
    }
}

@Composable
fun StepItem(
    step: InstructionStep,
    isActive: Boolean
) {
    val textColor by animateColorAsState(
        targetValue = if (isActive) Color.Black else Color.LightGray,
        animationSpec = tween(durationMillis = 400),
        label = "Step text color"
    )

    val fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${step.number}.",
            fontSize = 24.sp,
            fontWeight = fontWeight,
            color = textColor
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = step.title,
            fontSize = 24.sp,
            fontWeight = fontWeight,
            color = textColor
        )
    }
}

@SectionPreview
@Composable
fun InstructionsSectionPreview() {
    MaterialTheme {
        InstructionsSection(
            onClose = { /* Close action in preview */ }
        )
    }
}
