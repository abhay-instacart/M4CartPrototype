package com.instacart.caper.designtools.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.instacart.caper.designtools.R
import com.instacart.caper.designtools.data.provider.ItemProvider
import com.instacart.caper.designtools.ui.extensions.fadingEdges
import com.instacart.caper.designtools.ui.theme.CheckoutGreen
import com.instacart.caper.designtools.ui.theme.MiniCartBackground
import com.instacart.caper.designtools.ui.theme.MiniCartEmptyStateBackground
import com.instacart.caper.designtools.ui.theme.MiniCartEmptyStateIcon
import com.instacart.caper.designtools.ui.theme.MiniCartEmptyStateText
import com.instacart.caper.designtools.ui.viewmodel.CartItem
import com.instacart.caper.designtools.ui.viewmodel.CartState
import kotlinx.coroutines.delay

@Composable
fun MiniCart(
    cartState: CartState,
    modifier: Modifier = Modifier,
    onRemoveItem: (String) -> Unit = {},
    onProduceAnimationComplete: () -> Unit = {},
    onCheckoutClick: () -> Unit = {}
) {
    var previousItemCount by remember { mutableIntStateOf(cartState.itemCount) }
    var shouldAnimate by remember { mutableStateOf(false) }

    // Trigger animation when item count increases
    LaunchedEffect(cartState.itemCount) {
        val currentCount = cartState.itemCount
        val previousCount = previousItemCount

        // Update previous count immediately
        previousItemCount = currentCount

        if (currentCount > previousCount) {
            // Item added - trigger animation
            shouldAnimate = true
            delay(2000) // Duration of the animation - 2 seconds
            shouldAnimate = false
        } else if (currentCount < previousCount) {
            // Item removed - immediately stop animation
            shouldAnimate = false
        }
    }

    // Animate the glow alpha
    val glowAlpha by animateFloatAsState(
        targetValue = if (shouldAnimate) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "glow_alpha"
    )

    Box(modifier = modifier) {
        // Main cart content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .shadow(24.dp)
                .background(MiniCartBackground)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Checkout button with badge
            CheckoutButton(
                itemCount = cartState.itemCount,
                onClick = onCheckoutClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Cart items or empty state
            if (cartState.items.isEmpty()) {
                CartEmptyState(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            } else {
                CartItemsList(
                    items = cartState.items,
                    recentlyAddedItemId = cartState.recentlyAddedItemId,
                    onRemoveItem = onRemoveItem,
                    onProduceAnimationComplete = onProduceAnimationComplete,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }

        // Left edge glow
        Box(
            modifier = Modifier
                .width(48.dp)
                .fillMaxHeight()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            CheckoutGreen.copy(alpha = glowAlpha * 0.3f),
                            Color.Transparent
                        )
                    )
                )
        )

        // Right edge glow
        Box(
            modifier = Modifier
                .width(48.dp)
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            CheckoutGreen.copy(alpha = glowAlpha * 0.3f)
                        )
                    )
                )
        )
    }
}

@Composable
fun CheckoutButton(
    itemCount: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val isEmpty = itemCount == 0
    val buttonColor by animateColorAsState(
        targetValue = if (isEmpty) MiniCartEmptyStateBackground else CheckoutGreen,
        animationSpec = tween(durationMillis = 300),
        label = "button_color"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isEmpty) MiniCartEmptyStateIcon else Color.White,
        animationSpec = tween(durationMillis = 300),
        label = "content_color"
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(108.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            disabledContainerColor = buttonColor
        ),
        shape = RoundedCornerShape(24.dp),
        enabled = !isEmpty
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BadgedBox(
                badge = {
                    if (itemCount > 0) {
                        Badge(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                            modifier = Modifier
                                .size(24.dp)
                        ) {
                            Text(
                                text = itemCount.toString(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                            )
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = contentColor,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Checkout",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }
    }
}

@Composable
fun CartEmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Placeholder groceries illustration
        Image(
            painter = painterResource(id = R.drawable.empty_cart),
            contentDescription = "No items",
            modifier = Modifier.size(104.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "No items",
            fontSize = 16.sp,
            color = MiniCartEmptyStateText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun CartItemsList(
    items: List<CartItem>,
    modifier: Modifier = Modifier,
    recentlyAddedItemId: String? = null,
    onRemoveItem: (String) -> Unit = {},
    onProduceAnimationComplete: () -> Unit = {}
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = modifier.fadingEdges(
            listState,
            fadeLength = 16.dp,
            fadeColor = MiniCartBackground
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            items = items,
            key = { cartItem -> cartItem.id }
        ) { cartItem ->
            CartItemRow(
                cartItem = cartItem,
                showProduceAnimation = cartItem.id == recentlyAddedItemId,
                onRemoveItem = onRemoveItem,
                onAnimationComplete = onProduceAnimationComplete,
                modifier = Modifier.animateItem()
            )
        }
    }
}

@Preview
@Composable
fun MiniCartEmptyPreview() {
    MaterialTheme {
        Box(modifier = Modifier.height(800.dp)) {
            MiniCart(
                cartState = CartState(),
                onRemoveItem = {},
                onProduceAnimationComplete = {},
                onCheckoutClick = {},
                modifier = Modifier
                    .width(168.dp)
                    .fillMaxHeight()
            )
        }
    }
}

@Preview
@Composable
fun MiniCartWithItemsPreview() {
    val itemList = listOf(
        CartItem(groceryItem = ItemProvider.getAllCatalogItems()[9], quantity = 1), // Peanut Butter
        CartItem(groceryItem = ItemProvider.getAllCatalogItems()[6], quantity = 2), // Ice Cream
        CartItem(groceryItem = ItemProvider.getAllProduceItems()[0], quantity = 1), // Apples
        CartItem(groceryItem = ItemProvider.getAllProduceItems()[8], weight = 1.82) // Lemon
    )
    val itemCount = itemList.sumOf { it.quantity }
    val subtotal = itemList.sumOf { it.groceryItem.price * it.quantity }

    MaterialTheme {
        Box(modifier = Modifier.height(800.dp)) {
            MiniCart(
                cartState = CartState(
                    items = itemList,
                    subtotal = subtotal,
                    itemCount = itemCount
                ),
                onRemoveItem = {},
                onProduceAnimationComplete = {},
                onCheckoutClick = {},
                modifier = Modifier
                    .width(168.dp)
                    .fillMaxHeight()
            )
        }
    }
}
