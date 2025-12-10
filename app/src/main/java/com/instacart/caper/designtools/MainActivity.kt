package com.instacart.caper.designtools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.instacart.caper.designtools.ui.screens.HomeScreen
import com.instacart.caper.designtools.ui.screens.ProduceSearchScreen
import com.instacart.caper.designtools.ui.theme.M4CartPrototypeTheme
import com.instacart.caper.designtools.ui.viewmodel.CartViewModel
import com.instacart.caper.designtools.utils.SoundPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        // Hide system bars (status bar and navigation bar)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.apply {
            // Hide both status bar and navigation bar
            hide(WindowInsetsCompat.Type.systemBars())
            // Configure behavior when swiping from the edge
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContent {
            M4CartPrototypeTheme {
                val cartViewModel: CartViewModel = viewModel()
                val cartState by cartViewModel.cartState.collectAsState()
                val shouldShowCartAutomatically by cartViewModel.shouldShowCartAutomatically.collectAsState()
                val navController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            HomeScreen(
                                cartState = cartState,
                                onAddItem = { item ->
                                    // Add the selected item to cart
                                    cartViewModel.addItem(item)
                                },
                                onRemoveItem = { barcode ->
                                    // Remove item from cart by barcode
                                    cartViewModel.removeItem(barcode)
                                },
                                onNavigateToProduceSearch = {
                                    navController.navigate("produce_search")
                                },
                                onProduceAnimationComplete = {
                                    // Clear the produce animation flag after animation completes
                                    cartViewModel.clearProduceAnimation()
                                },
                                shouldShowCartAutomatically = shouldShowCartAutomatically,
                                onResetAutoShowCartFlag = {
                                    cartViewModel.resetAutoShowCartFlag()
                                }
                            )
                        }
                        composable("produce_search") {
                            ProduceSearchScreen(
                                onNavigateBack = {
                                    navController.popBackStack()
                                },
                                onAddItemWithPrice = { item, weight, totalPrice ->
                                    // Navigate back first, then add item after delay
                                    navController.popBackStack()
                                    coroutineScope.launch {
                                        delay(500) // Wait for navigation animation to complete
                                        cartViewModel.addItemWithPrice(item, totalPrice, weight)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SoundPlayer.release()
    }
}
