package com.instacart.caper.designtools.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.instacart.caper.designtools.R
import com.instacart.caper.designtools.data.model.GroceryItem
import com.instacart.caper.designtools.utils.SoundPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

data class CartItem(
    val id: String = UUID.randomUUID().toString(), // Unique ID for each cart item
    val groceryItem: GroceryItem,
    val quantity: Int = 1,
    val customPrice: Double? = null, // For produce items with weight
    val weight: Double? = null // For produce items
)

data class CartState(
    val items: List<CartItem> = emptyList(),
    val subtotal: Double = 0.0,
    val itemCount: Int = 0,
    val savings: Double = 0.0,
    val recentlyAddedItemId: String? = null
)

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val _cartState = MutableStateFlow(CartState())
    val cartState: StateFlow<CartState> = _cartState.asStateFlow()

    // Track if the cart has been shown automatically for the first time
    private var hasShownCartFirstTime = false

    // State flow to notify when cart should be shown automatically
    private val _shouldShowCartAutomatically = MutableStateFlow(false)
    val shouldShowCartAutomatically: StateFlow<Boolean> = _shouldShowCartAutomatically.asStateFlow()

    fun addItem(item: GroceryItem) {
        addItemWithPrice(item, null, null)
    }

    fun addItemWithPrice(item: GroceryItem, customPrice: Double?, weight: Double?) {
        val wasCartEmpty = _cartState.value.items.isEmpty()
        val currentItems = _cartState.value.items.toMutableList()
        var recentlyAddedId: String? = null

        // For produce items with custom price, always add as a new item (don't increment quantity)
        if (customPrice != null) {
            val newItem = CartItem(
                groceryItem = item,
                quantity = 1,
                customPrice = customPrice,
                weight = weight
            )
            currentItems.add(0, newItem)
            recentlyAddedId = newItem.id
        } else {
            val existingItemIndex = currentItems.indexOfFirst {
                it.groceryItem.barcode == item.barcode && it.customPrice == null
            }

            if (existingItemIndex >= 0) {
                // Item exists, increase quantity
                val existingItem = currentItems[existingItemIndex]
                currentItems[existingItemIndex] =
                    existingItem.copy(quantity = existingItem.quantity + 1)
                recentlyAddedId = existingItem.id
            } else {
                // New item, add to top of cart
                val newItem = CartItem(groceryItem = item, quantity = 1)
                currentItems.add(0, newItem)
                recentlyAddedId = newItem.id
            }
        }

        // Play sound when item is added
        Log.d("CartViewModel", "Playing sound for item addition")
        try {
            SoundPlayer.playSound(getApplication(), R.raw.sound_add)
        } catch (e: Exception) {
            Log.e("CartViewModel", "Error playing sound: ${e.message}")
        }

        updateCartState(currentItems, recentlyAddedId)

        // Show cart automatically if this is the first item ever added and we haven't shown it before
        if (wasCartEmpty && !hasShownCartFirstTime) {
            hasShownCartFirstTime = true
            _shouldShowCartAutomatically.value = true
        }
    }

    fun removeItem(id: String) {
        val currentItems = _cartState.value.items.toMutableList()
        val existingItemIndex = currentItems.indexOfFirst { it.id == id }

        if (existingItemIndex >= 0) {
            val existingItem = currentItems[existingItemIndex]
            if (existingItem.quantity > 1 && existingItem.customPrice == null) {
                // Decrease quantity (only for non-produce items)
                currentItems[existingItemIndex] =
                    existingItem.copy(quantity = existingItem.quantity - 1)
            } else {
                // Remove item from cart
                currentItems.removeAt(existingItemIndex)
            }
        }

        updateCartState(currentItems)
    }

    fun clearCart() {
        _cartState.value = CartState()
    }

    fun clearProduceAnimation() {
        _cartState.value = _cartState.value.copy(recentlyAddedItemId = null)
    }

    fun resetAutoShowCartFlag() {
        _shouldShowCartAutomatically.value = false
    }

    private fun updateCartState(
        items: List<CartItem>,
        recentlyAddedItemId: String? = null
    ) {
        val subtotal = items.sumOf { cartItem ->
            // Use custom price if available, otherwise use item price
            val price = cartItem.customPrice ?: cartItem.groceryItem.price
            price * cartItem.quantity
        }
        val itemCount = items.sumOf { it.quantity }
        val savings = itemCount * 1.00 // $1.00 per item

        _cartState.value = CartState(
            items = items,
            subtotal = subtotal,
            itemCount = itemCount,
            savings = savings,
            recentlyAddedItemId = recentlyAddedItemId
        )
    }
}
