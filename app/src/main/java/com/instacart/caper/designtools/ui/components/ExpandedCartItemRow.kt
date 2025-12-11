package com.instacart.caper.designtools.ui.components

import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.instacart.caper.designtools.data.provider.ItemProvider
import com.instacart.caper.designtools.ui.theme.CheckoutGreen
import com.instacart.caper.designtools.ui.viewmodel.CartItem
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun ExpandedCartItemRow(
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
                // After fade out completes, hide progress
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
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onRemoveItem(cartItem.id) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .animateContentSize(
                animationSpec = tween(durationMillis = 300)
            )
    ) {
        // Main row with image, title, and price
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product image with quantity badge
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                if (imageBitmap != null) {
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = cartItem.groceryItem.name,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .size(72.dp),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = cartItem.groceryItem.name,
                        tint = Color(0xFFE0E0E0),
                        modifier = Modifier.size(72.dp)
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
                            .offset(x = 16.dp, y = 2.dp)
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

            // Product title
            Text(
                text = cartItem.groceryItem.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )

            // Price
            val itemPrice = cartItem.customPrice ?: cartItem.groceryItem.price
            val totalPrice = itemPrice * cartItem.quantity
            Text(
                text = "$${String.format(Locale.US, "%.2f", totalPrice)}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        // Progress indicator row (shown below main content)
        AnimatedVisibility(
            visible = showProgress,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    // Circular progress indicator
                    Box(
                        modifier = Modifier.size(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            progress = { animatedProgress },
                            modifier = Modifier.size(48.dp),
                            color = CheckoutGreen.copy(alpha = animatedAlpha),
                            strokeWidth = 5.dp,
                            trackColor = Color(0xFFE0E0E0).copy(alpha = animatedAlpha)
                        )
                    }


                    // Text
                    Text(
                        text = "Place this item in the cart",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black.copy(alpha = animatedAlpha)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 640)
@Composable
fun ExpandedCartItemRowPreview() {
    MaterialTheme {
        ExpandedCartItemRow(
            cartItem = CartItem(
                groceryItem = ItemProvider.getAllCatalogItems()[9], // Peanut Butter
                quantity = 1
            ),
            onRemoveItem = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 640)
@Composable
fun ExpandedCartItemRowMultiplePreview() {
    MaterialTheme {
        ExpandedCartItemRow(
            cartItem = CartItem(
                groceryItem = ItemProvider.getAllCatalogItems()[6], // Ice Cream
                quantity = 2
            ),
            onRemoveItem = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 640)
@Composable
fun ExpandedCartItemRowWithAnimationPreview() {
    MaterialTheme {
        ExpandedCartItemRow(
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
