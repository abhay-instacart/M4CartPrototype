package com.instacart.caper.designtools.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.instacart.caper.designtools.R
import com.instacart.caper.designtools.data.model.GroceryItem
import com.instacart.caper.designtools.data.provider.ItemProvider
import com.instacart.caper.designtools.ui.Fire10Preview
import com.instacart.caper.designtools.ui.Fire11Preview
import com.instacart.caper.designtools.ui.components.ProduceItemBottomSheet
import com.instacart.caper.designtools.ui.components.ProduceItemCard
import com.instacart.caper.designtools.ui.extensions.fadingEdges
import com.instacart.caper.designtools.ui.theme.BackgroundBeige

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProduceSearchScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onAddItemWithPrice: (GroceryItem, Double, Double) -> Unit = { _, _, _ -> }
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedItem by remember { mutableStateOf<GroceryItem?>(null) }
    var isCartMode by remember { mutableStateOf(true) }
    val allProduceItems = remember { ItemProvider.getAllProduceItems() }

    // Filter items based on search query (by title or barcode)
    val filteredItems = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            allProduceItems
        } else {
            allProduceItems.filter { item ->
                item.name.contains(searchQuery, ignoreCase = true) ||
                        item.barcode.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundBeige)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top bar with back button and search field
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Back button
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onNavigateBack)
                )

                Spacer(modifier = Modifier.weight(1f))

                // Search field
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    placeholder = {
                        Text(
                            text = "Search item by name or code",
                            style = TextStyle(
                                fontSize = 32.sp,
                                color = Color.Gray.copy(alpha = 0.5f)
                            )
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Black,
                            modifier = Modifier
                                .padding(start = 24.dp, end = 8.dp)
                                .size(40.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear search",
                                tint = Color.Black,
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 24.dp)
                                    .size(40.dp)
                                    .clickable(onClick = {
                                        searchQuery = ""
                                    })
                            )
                        }
                    },
                    textStyle = TextStyle(
                        fontSize = 32.sp,
                        color = Color.Black
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(32.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.weight(1f))

                // Toggle icon between cart and scale
                Icon(
                    painter = painterResource(
                        id = if (isCartMode) R.drawable.ids_icon_cart else R.drawable.ids_icon_scale
                    ),
                    contentDescription = "Toggle title",
                    tint = Color.Black.copy(alpha = 0.1f),
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable { isCartMode = !isCartMode }
                )
            }

            // Grid of produce items
            val gridState = rememberLazyGridState()
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(5),
                modifier = Modifier
                    .fillMaxSize()
                    .fadingEdges(gridState, fadeLength = 24.dp, fadeColor = BackgroundBeige),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredItems) { item ->
                    ProduceItemCard(
                        item = item,
                        onClick = { selectedItem = item }
                    )
                }
            }
        }

        // Show bottom sheet when an item is selected
        selectedItem?.let { item ->
            ProduceItemBottomSheet(
                item = item,
                title = if (isCartMode) "Place item in cart to weigh" else "Place item on scale to weigh",
                onDismiss = { selectedItem = null },
                onConfirm = { weight, totalPrice ->
                    // Add item to cart with custom price (navigation handled by MainActivity)
                    onAddItemWithPrice(item, weight, totalPrice)
                    selectedItem = null
                }
            )
        }
    }
}

@Fire10Preview
@Fire11Preview
@Composable
fun ProduceSearchScreenPreview() {
    MaterialTheme {
        ProduceSearchScreen()
    }
}
