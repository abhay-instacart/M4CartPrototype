package com.instacart.caper.designtools.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.instacart.caper.designtools.R
import com.instacart.caper.designtools.data.model.GroceryItem
import com.instacart.caper.designtools.data.provider.ItemProvider
import com.instacart.caper.designtools.ui.Fire10Preview
import com.instacart.caper.designtools.ui.Fire11Preview
import com.instacart.caper.designtools.ui.components.ExpandedCart
import com.instacart.caper.designtools.ui.components.MiniCart
import com.instacart.caper.designtools.ui.components.NavigationAction
import com.instacart.caper.designtools.ui.components.NavigationBar
import com.instacart.caper.designtools.ui.components.ScaleInfo
import com.instacart.caper.designtools.ui.screens.section.CatalogSection
import com.instacart.caper.designtools.ui.screens.section.InstructionsSection
import com.instacart.caper.designtools.ui.theme.BackgroundBeige
import com.instacart.caper.designtools.ui.theme.CheckoutGreen
import com.instacart.caper.designtools.ui.viewmodel.CartItem
import com.instacart.caper.designtools.ui.viewmodel.CartState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    cartState: CartState = CartState(),
    onAddItem: (GroceryItem) -> Unit = {},
    onRemoveItem: (String) -> Unit = {},
    onNavigateToProduceSearch: () -> Unit = {},
    onProduceAnimationComplete: () -> Unit = {},
    shouldShowCartAutomatically: Boolean = false,
    onResetAutoShowCartFlag: () -> Unit = {},
    initialShowInstructions: Boolean = false
) {
    val focusRequester = remember { FocusRequester() }
    var isExpandedCartVisible by remember { mutableStateOf(false) }
    var showInstructions by remember { mutableStateOf(initialShowInstructions) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    // Auto-show cart when first item is added
    LaunchedEffect(shouldShowCartAutomatically) {
        if (shouldShowCartAutomatically) {
            isExpandedCartVisible = true
            onResetAutoShowCartFlag()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundBeige)
            .focusRequester(focusRequester)
            .onKeyEvent { keyEvent ->
                if (!showInstructions && keyEvent.type == KeyEventType.KeyDown) {
                    when (keyEvent.key) {
                        Key.A -> {
                            // Add random item
                            onAddItem(ItemProvider.getRandomCatalogItem())
                            true
                        }

                        Key.One -> {
                            // Add Sushi (index 10)
                            onAddItem(ItemProvider.getAllCatalogItems()[10])
                            true
                        }

                        Key.Two -> {
                            // Add Birthday Cake (index 1)
                            onAddItem(ItemProvider.getAllCatalogItems()[1])
                            true
                        }

                        Key.Three -> {
                            // Add Charmin Toilet Paper (index 11)
                            onAddItem(ItemProvider.getAllCatalogItems()[11])
                            true
                        }

                        Key.Four -> {
                            // Add Poland Spring Water (index 13)
                            onAddItem(ItemProvider.getAllCatalogItems()[13])
                            true
                        }

                        Key.Five -> {
                            // Add Purina One Dog Food (index 3)
                            onAddItem(ItemProvider.getAllCatalogItems()[3])
                            true
                        }

                        else -> false
                    }
                } else {
                    false
                }
            }
            .focusable()
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // Left side with navigation bar and main content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                // Navigation bar at the top
                NavigationBar(
                    subtotal = cartState.subtotal,
                    savings = cartState.savings,
                    showHowToButton = !showInstructions,
                    onNavigationAction = { action ->
                        when (action) {
                            NavigationAction.HowTo -> showInstructions = true
                            NavigationAction.Menu -> { /* Handle menu */
                            }

                            NavigationAction.Coupons -> { /* Handle coupons */
                            }

                            NavigationAction.Search -> { /* Handle search */
                            }
                        }
                    }
                )

                // Main content area below navigation
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Conditionally show either CatalogSection or InstructionsSection
                    if (showInstructions) {
                        InstructionsSection(
                            onClose = { showInstructions = false }
                        )
                    } else {
                        CatalogSection(
                            items = ItemProvider.getAllCatalogItems(),
                            onItemClick = onAddItem
                        )
                    }

                    ScaleInfo(
                        cartState = cartState,
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.BottomStart)
                            .padding(start = 24.dp, bottom = 8.dp)
                    )

                    // Floating Action Button container at the bottom (hidden when instructions are showing)
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 16.dp, end = 24.dp)
                    ) {
                        Crossfade(
                            targetState = showInstructions,
                            label = "FAB visibility"
                        ) { isShowingInstructions ->
                            if (!isShowingInstructions) {
                                AddItemFloatingButton(
                                    onClick = onNavigateToProduceSearch
                                )
                            }
                        }
                    }
                }
            }

            // Mini cart (right side)
            MiniCart(
                cartState = cartState,
                onRemoveItem = onRemoveItem,
                onProduceAnimationComplete = onProduceAnimationComplete,
                onCheckoutClick = {
                    if (cartState.itemCount > 0) {
                        isExpandedCartVisible = true
                    }
                },
                modifier = Modifier
                    .width(168.dp)
                    .fillMaxHeight()
            )
        }

        // Expanded cart drawer
        ExpandedCart(
            isVisible = isExpandedCartVisible,
            cartState = cartState,
            onDismiss = { isExpandedCartVisible = false },
            onRemoveItem = onRemoveItem,
            onProduceAnimationComplete = onProduceAnimationComplete,
            onCheckout = {
                // Handle checkout action
                isExpandedCartVisible = false
            }
        )
    }
}

@Composable
fun AddItemFloatingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.height(80.dp),
        containerColor = CheckoutGreen,
        shape = RoundedCornerShape(50)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ids_icon_apple),
                contentDescription = "Add item",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Add item by name or code",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Fire10Preview
@Composable
fun HomeScreenWithCartItemsPreview() {
    MaterialTheme {
        val item = CartItem(
            groceryItem = ItemProvider.getAllCatalogItems()[7],
            quantity = 1
        ) // Peanut butter
        val itemList = listOf(item)
        // Preview with items
        val sampleCartState = CartState(
            items = itemList,
            subtotal = item.groceryItem.price,
            itemCount = itemList.size,
            savings = itemList.size * 1.0
        )
        HomeScreen(
            cartState = sampleCartState,
            onAddItem = { _: GroceryItem -> /* Add item in actual implementation */ },
            onRemoveItem = { /* Remove item in actual implementation */ },
            onNavigateToProduceSearch = { /* Navigate to produce search in actual implementation */ },
            onProduceAnimationComplete = { /* onProduceAnimationComplete callback */ },
            shouldShowCartAutomatically = false,
            onResetAutoShowCartFlag = { }
        )
    }
}

@Fire11Preview
@Composable
fun HomeScreenWithoutCartItemsPreview() {
    MaterialTheme {
        // Preview with empty cart
        HomeScreen(
            cartState = CartState(),
            onAddItem = { _: GroceryItem -> /* Add item in actual implementation */ },
            onRemoveItem = { /* Remove item in actual implementation */ },
            onNavigateToProduceSearch = { /* Navigate to produce search in actual implementation */ },
            onProduceAnimationComplete = { /* onProduceAnimationComplete callback */ },
            shouldShowCartAutomatically = false,
            onResetAutoShowCartFlag = { }
        )
    }
}

@Fire11Preview
@Composable
fun HomeScreenWithExpandedCartPreview() {
    MaterialTheme {
        val itemList = listOf(
            CartItem(
                groceryItem = ItemProvider.getAllCatalogItems()[7],
                quantity = 1
            ), // Peanut butter
            CartItem(groceryItem = ItemProvider.getAllCatalogItems()[4], quantity = 2), // Ice cream
            CartItem(groceryItem = ItemProvider.getAllProduceItems()[0], quantity = 1), // Apples
            CartItem(groceryItem = ItemProvider.getAllProduceItems()[6], weight = 1.82) // Lemons
        )
        val itemCount = itemList.sumOf { it.quantity }
        val subtotal = itemList.sumOf { it.groceryItem.price * it.quantity }

        // Preview with expanded cart visible
        val sampleCartState = CartState(
            items = itemList,
            subtotal = subtotal,
            itemCount = itemCount,
            savings = itemList.size * 1.0
        )

        HomeScreen(
            cartState = sampleCartState,
            onAddItem = { _: GroceryItem -> /* Add item in actual implementation */ },
            onRemoveItem = { /* Remove item in actual implementation */ },
            onNavigateToProduceSearch = { /* Navigate to produce search in actual implementation */ },
            onProduceAnimationComplete = { /* onProduceAnimationComplete callback */ },
            shouldShowCartAutomatically = true,
            onResetAutoShowCartFlag = { }
        )
    }
}

@Fire11Preview
@Composable
fun HomeScreenWithInstructionsPreview() {
    MaterialTheme {
        // Preview with instructions section showing
        HomeScreen(
            cartState = CartState(),
            onAddItem = { _: GroceryItem -> /* Add item in actual implementation */ },
            onRemoveItem = { /* Remove item in actual implementation */ },
            onNavigateToProduceSearch = { /* Navigate to produce search in actual implementation */ },
            onProduceAnimationComplete = { /* onProduceAnimationComplete callback */ },
            shouldShowCartAutomatically = false,
            onResetAutoShowCartFlag = { },
            initialShowInstructions = true
        )
    }
}
