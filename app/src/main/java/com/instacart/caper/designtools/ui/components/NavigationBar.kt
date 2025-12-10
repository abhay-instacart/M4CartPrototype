package com.instacart.caper.designtools.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.instacart.caper.designtools.R
import com.instacart.caper.designtools.ui.theme.BackgroundBeige

sealed class NavigationAction {
    data object Menu : NavigationAction()
    data object Coupons : NavigationAction()
    data object Search : NavigationAction()
    data object HowTo : NavigationAction()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    subtotal: Double = 0.0,
    savings: Double = 3.00,
    showHowToButton: Boolean = true,
    onNavigationAction: (NavigationAction) -> Unit = {}
) {
    var showDebugMenu by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .height(156.dp)
            .fillMaxWidth()
            .background(BackgroundBeige)
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side - Menu, Coupons, Search
        Row(
            modifier = Modifier.height(410.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Menu button with Fresh Grocery text
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .combinedClickable(
                        onClick = { onNavigationAction(NavigationAction.Menu) },
                        onLongClick = { showDebugMenu = true }
                    )
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        modifier = Modifier.size(32.dp),
                        tint = Color.Black
                    )
                    Column {
                        Text(
                            text = "Fresh",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Grocery",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }

            // Coupons button
            NavigationBarButton(
                iconRes = R.drawable.ids_icon_coupon,
                text = "Coupons",
                onClick = { onNavigationAction(NavigationAction.Coupons) }
            )

            // Search button
            NavigationBarButton(
                iconRes = R.drawable.ids_icon_search,
                text = "Search",
                onClick = { onNavigationAction(NavigationAction.Search) }
            )

            // How to button (conditionally shown with fade animation)
            AnimatedVisibility(
                visible = showHowToButton,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                NavigationBarButton(
                    iconRes = R.drawable.ids_icon_info,
                    text = "How to",
                    onClick = { onNavigationAction(NavigationAction.HowTo) }
                )
            }
        }

        // Right side - Savings and Subtotal
        CartSummary(
            savings = savings,
            subtotal = subtotal
        )
    }

    // Debug menu popup
    if (showDebugMenu) {
        KeyboardShortcutsDialog(
            onDismiss = { showDebugMenu = false }
        )
    }
}

@Composable
fun CartSummary(
    savings: Double,
    subtotal: Double,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        SavingsSubtotal(
            savings = savings,
            subtotal = subtotal,
            modifier = modifier
        )
    }
}

@Composable
fun NavigationBarButton(
    iconRes: Int,
    text: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.aspectRatio(1f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                modifier = Modifier.size(48.dp),
                tint = Color(0xFF43A047)
            )
            Text(
                text = text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}

@Preview(widthDp = 1280, heightDp = 156)
@Composable
fun NavigationBarPreview() {
    MaterialTheme {
        NavigationBar(
            subtotal = 44.94,
            savings = 3.00,
            showHowToButton = false,
            onNavigationAction = { action ->
                // Handle navigation action in preview
                println("Navigation action: $action")
            }
        )
    }
}

@Preview(widthDp = 1280, heightDp = 156)
@Composable
fun NavigationBarEmptyPreview() {
    MaterialTheme {
        NavigationBar(
            subtotal = 44.94,
            savings = 3.00,
            onNavigationAction = { action ->
                // Handle navigation action in preview
                println("Navigation action: $action")
            }
        )
    }
}
