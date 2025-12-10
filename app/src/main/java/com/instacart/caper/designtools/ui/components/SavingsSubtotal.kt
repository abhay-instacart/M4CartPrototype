package com.instacart.caper.designtools.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.instacart.caper.designtools.ui.theme.HighlightColor
import com.instacart.caper.designtools.ui.theme.OutlineColor
import java.util.Locale

@Composable
fun SavingsSubtotal(
    savings: Double,
    subtotal: Double,
    modifier: Modifier = Modifier
) {
    var previousSavings by remember { mutableDoubleStateOf(savings) }
    var previousSubtotal by remember { mutableDoubleStateOf(subtotal) }

    val savingsIncreasing = savings >= previousSavings
    val subtotalIncreasing = subtotal >= previousSubtotal

    // Update previous values after determining direction
    LaunchedEffect(savings) {
        previousSavings = savings
    }
    LaunchedEffect(subtotal) {
        previousSubtotal = subtotal
    }

    Row(
        modifier = modifier.padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Savings
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = savings,
                transitionSpec = {
                    (slideInVertically(
                        animationSpec = tween(300),
                        initialOffsetY = { offset ->
                            if (savingsIncreasing) -offset / 2 else offset / 2
                        }
                    ) + fadeIn(animationSpec = tween(300))).togetherWith(
                        slideOutVertically(
                            animationSpec = tween(300),
                            targetOffsetY = { offset ->
                                if (savingsIncreasing) offset / 2 else -offset / 2
                            }
                        ) + fadeOut(animationSpec = tween(300))
                    )
                },
                label = "savings_animation"
            ) { animatedSavings ->
                Text(
                    text = String.format(Locale.US, "$%.2f", animatedSavings),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .background(
                            color = HighlightColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(all = 4.dp)
                )
            }
            Text(
                text = "Savings",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .padding(vertical = 16.dp)
                .background(color = OutlineColor)
        )

        // Subtotal
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = subtotal,
                transitionSpec = {
                    (slideInVertically(
                        animationSpec = tween(300),
                        initialOffsetY = { offset ->
                            if (subtotalIncreasing) -offset / 2 else offset / 2
                        }
                    ) + fadeIn(animationSpec = tween(300))).togetherWith(
                        slideOutVertically(
                            animationSpec = tween(300),
                            targetOffsetY = { offset ->
                                if (subtotalIncreasing) offset / 2 else -offset / 2
                            }
                        ) + fadeOut(animationSpec = tween(300))
                    )
                },
                label = "subtotal_animation"
            ) { animatedSubtotal ->
                Text(
                    text = String.format(Locale.US, "$%.2f", animatedSubtotal),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(all = 4.dp)
                )
            }

            Text(
                text = "Subtotal",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
