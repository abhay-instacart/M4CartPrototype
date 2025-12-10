package com.instacart.caper.designtools.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.instacart.caper.designtools.data.provider.ItemProvider
import com.instacart.caper.designtools.ui.extensions.fadingEdges
import com.instacart.caper.designtools.ui.theme.CheckoutGreen
import com.instacart.caper.designtools.ui.viewmodel.CartItem
import com.instacart.caper.designtools.ui.viewmodel.CartState
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpandedCart(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    cartState: CartState,
    onDismiss: () -> Unit,
    onRemoveItem: (String) -> Unit = {},
    onProduceAnimationComplete: () -> Unit = {},
    onCheckout: () -> Unit = {}
) {
//    // Automatically hide drawer when cart becomes empty
//    LaunchedEffect(cartState.itemCount) {
//        if (isVisible && cartState.itemCount == 0) {
//            delay(1000)
//            onDismiss()
//        }
//    }

    // Track when drawer animation is complete to prevent flickering
    var isDrawerAnimationComplete by remember { mutableStateOf(false) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            // Reset the flag when drawer starts opening
            isDrawerAnimationComplete = false
            // Wait for drawer slide-in animation to complete (300ms)
            delay(350)
            isDrawerAnimationComplete = true
        } else {
            isDrawerAnimationComplete = false
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Scrim (dark overlay) - fades in/out
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onDismiss
                    )
            )
        }

        // Drawer content - slides in/out from right
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(300)
            ),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            val cornerRadius = 32.dp
            Card(
                modifier = Modifier
                    .width(720.dp)
                    .fillMaxHeight()
                    .shadow(24.dp),
                shape = RoundedCornerShape(topStart = cornerRadius, bottomStart = cornerRadius),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 24.dp, horizontal = 16.dp)
                            .weight(1f)
                    ) {
                        // Header with title and close button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 24.dp),
                                text = "Your cart",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            IconButton(
                                onClick = onDismiss,
                                modifier = Modifier.size(56.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color.Black,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Cart items list
                        val listState = rememberLazyListState()
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .fadingEdges(
                                    listState,
                                    fadeLength = 24.dp,
                                    fadeColor = Color.White
                                ),
                        ) {
                            items(
                                items = cartState.items,
                                key = { cartItem -> cartItem.id }
                            ) { cartItem ->
                                ExpandedCartItemRow(
                                    cartItem = cartItem,
                                    showProduceAnimation = cartItem.id == cartState.recentlyAddedItemId && isDrawerAnimationComplete,
                                    onRemoveItem = onRemoveItem,
                                    onAnimationComplete = onProduceAnimationComplete,
                                    modifier = Modifier.animateItem()
                                )
                            }
                        }
                    }
                    // Bottom section with savings, subtotal, and checkout button
                    ExpandedCartBottomSection(
                        modifier = Modifier
                            .height(128.dp)
                            .shadow(16.dp),
                        savings = cartState.savings,
                        subtotal = cartState.subtotal,
                        itemCount = cartState.itemCount,
                        onCheckout = onCheckout
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandedCartBottomSection(
    savings: Double,
    subtotal: Double,
    onCheckout: () -> Unit,
    modifier: Modifier = Modifier,
    itemCount: Int = 0
) {
    val isEnabled = itemCount > 0
    val buttonColor by animateColorAsState(
        targetValue = if (isEnabled) CheckoutGreen else Color.LightGray,
        animationSpec = tween(durationMillis = 300),
        label = "button_color_animation"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(128.dp)
            .background(Color.White)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Savings and Subtotal
        SavingsSubtotal(
            savings = savings,
            subtotal = subtotal,
            modifier = Modifier.padding(0.dp)
        )

        // Checkout button
        Button(
            onClick = onCheckout,
            enabled = isEnabled,
            modifier = Modifier
                .height(80.dp)
                .weight(1f)
                .padding(horizontal = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                disabledContainerColor = buttonColor
            ),
            shape = RoundedCornerShape(40.dp)
        ) {
            Text(
                text = "Start checkout",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 1024)
@Composable
fun ExpandedCartPreview() {
    val itemList = listOf(
        CartItem(groceryItem = ItemProvider.getAllCatalogItems()[9], quantity = 1), // Peanut Butter
        CartItem(groceryItem = ItemProvider.getAllCatalogItems()[6], quantity = 2), // Ice Cream
        CartItem(groceryItem = ItemProvider.getAllProduceItems()[0], quantity = 1), // Apples
        CartItem(groceryItem = ItemProvider.getAllProduceItems()[8], weight = 1.82) // Lemon
    )
    val itemCount = itemList.sumOf { it.quantity }
    val subtotal = itemList.sumOf { it.groceryItem.price * it.quantity }

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            ExpandedCart(
                isVisible = true,
                cartState = CartState(
                    items = itemList,
                    subtotal = subtotal,
                    itemCount = itemCount,
                    savings = 5.50
                ),
                onDismiss = {},
                onRemoveItem = {},
                onProduceAnimationComplete = {},
                onCheckout = {}
            )
        }
    }
}
