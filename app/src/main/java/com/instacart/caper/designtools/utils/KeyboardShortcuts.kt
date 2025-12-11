package com.instacart.caper.designtools.utils

import androidx.compose.ui.input.key.Key
import com.instacart.caper.designtools.data.model.GroceryItem
import com.instacart.caper.designtools.data.provider.ItemProvider

/**
 * Centralized keyboard shortcut mappings for the app.
 * Maps numbered keys to specific catalog items.
 */
object KeyboardShortcuts {

    data class KeyMapping(
        val key: Key,
        val keyValue: String,
        val item: GroceryItem?,
    ) {
        val description: String = "Add ${item?.name ?: "unknown"}"
    }

    /**
     * Mappings for numbered keys to catalog items
     */
    val numberedKeyMappings = listOf(
        KeyMapping(
            key = Key.One,
            keyValue = "1",
            item = ItemProvider.getAllCatalogItems().getOrNull(10)  // Fresh Sushi Roll
        ),
        KeyMapping(
            key = Key.Two,
            keyValue = "2",
            item = ItemProvider.getAllCatalogItems().getOrNull(1)   // Birthday Cake
        ),
        KeyMapping(
            key = Key.Three,
            keyValue = "3",
            item = ItemProvider.getAllCatalogItems().getOrNull(11)  // Charmin - Ultra Soft Toilet Paper
        ),
        KeyMapping(
            key = Key.Four,
            keyValue = "4",
            item = ItemProvider.getAllCatalogItems().getOrNull(13) // Poland Spring Water
        ),
        KeyMapping(
            key = Key.Five,
            keyValue = "5",
            item = ItemProvider.getAllCatalogItems().getOrNull(3) // Purina ONE Dry Dog Food - Lamb & Rice
        ),
    )

    /**
     * Get the grocery item for a specific key mapping
     */
    fun getItemForKey(key: Key): GroceryItem? {
        return numberedKeyMappings.find { it.key == key }?.item
    }
}
