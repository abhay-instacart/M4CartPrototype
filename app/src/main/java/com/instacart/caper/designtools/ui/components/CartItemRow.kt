package com.instacart.caper.designtools.ui.components

import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.instacart.caper.designtools.data.provider.ItemProvider
import com.instacart.caper.designtools.ui.theme.CheckoutGreen
import com.instacart.caper.designtools.ui.viewmodel.CartItem
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun CartItemRow(
    cartItem: CartItem,
    modifier: Modifier = Modifier,
    showProduceAnimation: Boolean = false,
    onRemoveItem: (String) -> Unit = {},
    onAnimationComplete: () -> Unit = {}
) {
    val context = LocalContext.current
    val imageBitmap = remember(cartItem.groceryItem.imagePath) {
        try {
            val inputStream = context.assets.open(cartItem.groceryItem.imagePath)
            BitmapFactory.decodeStream(inputStream)?.asImageBitmap()
        } catch (e: Exception) {
            null
        }
    }

    // Animation state
    var showProgress by remember(showProduceAnimation) { mutableStateOf(showProduceAnimation) }
    var targetProgress by remember(showProduceAnimation) { mutableFloatStateOf(0f) }
    var targetAlpha by remember(showProduceAnimation) { mutableFloatStateOf(1f) }

    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 2500),
        label = "progress_animation",
        finishedListener = {
            if (it >= 1f) {
                // Start fading out when progress reaches 100%
                targetAlpha = 0f
            }
        }
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = 300),
        label = "alpha_animation",
        finishedListener = {
            if (it <= 0f) {
                // After fade out completes, hide progress and trigger resize
                showProgress = false
                onAnimationComplete()
            }
        }
    )

    // Start animation when showProduceAnimation becomes true
    LaunchedEffect(showProduceAnimation) {
        if (showProduceAnimation) {
            showProgress = true
            targetAlpha = 1f
            delay(50) // Small delay to ensure animation triggers
            targetProgress = 1f
        }
    }

    Column(
        modifier = modifier
            .size(
                width = 120.dp,
                height = if (showProgress) 160.dp else 120.dp
            )
            .animateContentSize(
                animationSpec = tween(durationMillis = 300)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable { onRemoveItem(cartItem.id) }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Item image with quantity badge
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = cartItem.groceryItem.name,
                    modifier = Modifier
                        .size(72.dp),
                    contentScale = ContentScale.Fit
                )
            } else {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = cartItem.groceryItem.name,
                    tint = Color(0xFFE0E0E0),
                    modifier = Modifier
                        .size(72.dp)
                )
            }

            // Badge (shows quantity if > 1, otherwise shows weight for produce items)
            val badgeText = when {
                cartItem.quantity > 1 -> "x${cartItem.quantity}"
                cartItem.weight != null -> "%.2f lb".format(cartItem.weight)
                else -> null
            }

            badgeText?.let { text ->
                Badge(
                    containerColor = CheckoutGreen,
                    contentColor = Color.White,
                    modifier = Modifier
                        .offset(x = 16.dp, y = (-4).dp)
                ) {
                    Text(
                        text = text,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Price (total for all items)
        val itemPrice = cartItem.customPrice ?: cartItem.groceryItem.price
        val totalPrice = itemPrice * cartItem.quantity
        Text(
            text = "$${String.format(Locale.US, "%.2f", totalPrice)}",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        // Circular progress indicator (only shown when showProgress is true)
        AnimatedVisibility(
            visible = showProgress,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                // Animated progress (green)
                CircularProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.size(36.dp),
                    color = CheckoutGreen.copy(alpha = animatedAlpha),
                    strokeWidth = 4.dp,
                    trackColor = Color(0xFFE0E0E0).copy(alpha = animatedAlpha)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemRowPreview() {
    MaterialTheme {
        CartItemRow(
            cartItem = CartItem(
                groceryItem = ItemProvider.getAllCatalogItems()[6], // Ice Cream
                quantity = 2
            ),
            onRemoveItem = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemRowWithProgressPreview() {
    MaterialTheme {
        CartItemRow(
            cartItem = CartItem(
                groceryItem = ItemProvider.getAllProduceItems()[0], // Apples (produce item)
                quantity = 1,
                customPrice = 3.45,
                weight = 1.5
            ),
            showProduceAnimation = true,
            onRemoveItem = {}
        )
    }
}
