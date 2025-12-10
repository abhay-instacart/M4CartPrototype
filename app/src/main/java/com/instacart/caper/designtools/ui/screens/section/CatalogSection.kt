package com.instacart.caper.designtools.ui.screens.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.instacart.caper.designtools.data.model.GroceryItem
import com.instacart.caper.designtools.data.provider.ItemProvider
import com.instacart.caper.designtools.ui.Fire11Preview
import com.instacart.caper.designtools.ui.SectionPreview
import com.instacart.caper.designtools.ui.components.CatalogItemCard
import com.instacart.caper.designtools.ui.extensions.fadingEdges

@Composable
fun CatalogSection(
    items: List<GroceryItem>,
    onItemClick: (GroceryItem) -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, bottom = 40.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 40.dp, top = 40.dp, end = 40.dp)
        ) {
            // Title
            Text(
                text = "Deals for you",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "Snacks",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Scrollable catalog
            val gridState = rememberLazyGridState()
            LazyVerticalGrid(
                state = gridState,
                modifier = Modifier
                    .fillMaxWidth()
                    .fadingEdges(gridState, fadeLength = 24.dp, fadeColor = Color.White),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 40.dp)
            ) {
                items(items = items) { item ->
                    CatalogItemCard(
                        item = item,
                        context = context,
                        onClick = { onItemClick(item) }
                    )
                }
            }
        }
    }
}

@SectionPreview
@Composable
fun CatalogSectionPreview() {
    MaterialTheme {
        CatalogSection(
            items = ItemProvider.getAllCatalogItems(),
            onItemClick = {}
        )
    }
}
