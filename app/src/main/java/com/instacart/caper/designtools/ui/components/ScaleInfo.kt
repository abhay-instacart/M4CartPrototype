package com.instacart.caper.designtools.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.instacart.caper.designtools.R
import com.instacart.caper.designtools.ui.viewmodel.CartState

/**
 * Displays scale specifications and the total weight of items in the cart.
 *
 * @param cartState The cart state containing items with weights
 * @param modifier Modifier to be applied to the component
 */
@Composable
fun ScaleInfo(
    cartState: CartState,
    modifier: Modifier = Modifier
) {
    // Calculate total weight from all items in cart (only items with weight)
    val totalWeight = cartState.items.mapNotNull { it.weight }.sum()

    var previousWeight by remember { mutableDoubleStateOf(totalWeight) }
    val weightGoingToZero = totalWeight == 0.0 && previousWeight > 0.0

    // Update previous weight after determining direction
    LaunchedEffect(totalWeight) {
        previousWeight = totalWeight
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ids_icon_produce_scale),
            contentDescription = "Produce scale icon",
            tint = Color.DarkGray,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.size(8.dp))

        // Static part of the text
        Text(
            text = "Scale specs: 80 x 0.01 lb • Weight: ",
            fontSize = 16.sp,
            color = Color.DarkGray
        )

        // Animated weight value
        AnimatedContent(
            targetState = totalWeight,
            transitionSpec = {
                (slideInVertically(
                    animationSpec = tween(300),
                    initialOffsetY = { offset ->
                        if (weightGoingToZero) offset / 2 else -offset / 2
                    }
                ) + fadeIn(animationSpec = tween(300))).togetherWith(
                    slideOutVertically(
                        animationSpec = tween(300),
                        targetOffsetY = { offset ->
                            if (weightGoingToZero) -offset / 2 else offset / 2
                        }
                    ) + fadeOut(animationSpec = tween(300))
                )
            },
            label = "weight_animation"
        ) { animatedWeight ->
            Text(
                text = "%.2f lb".format(animatedWeight),
                fontSize = 16.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Preview
@Composable
fun ScaleInfoPreview() {
    MaterialTheme {
        ScaleInfo(cartState = CartState())
    }
}

@Preview
@Composable
fun ScaleInfoWithWeightPreview() {
    MaterialTheme {
        // Mock cart state with some weight
        val mockCartState = CartState(
            items = listOf(
                com.instacart.caper.designtools.ui.viewmodel.CartItem(
                    groceryItem = com.instacart.caper.designtools.data.provider.ItemProvider.getAllProduceItems()[0],
                    weight = 2.45
                ),
                com.instacart.caper.designtools.ui.viewmodel.CartItem(
                    groceryItem = com.instacart.caper.designtools.data.provider.ItemProvider.getAllProduceItems()[1],
                    weight = 1.32
                )
            )
        )
        ScaleInfo(cartState = mockCartState)
    }
}
