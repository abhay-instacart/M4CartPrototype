package com.instacart.caper.designtools.data.model

data class GroceryItem(
    val imagePath: String,
    val title: String,
    val price: Double,
    val weight: String,
    val barcode: String
)
