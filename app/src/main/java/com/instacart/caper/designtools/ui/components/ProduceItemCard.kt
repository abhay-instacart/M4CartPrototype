package com.instacart.caper.designtools.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.instacart.caper.designtools.data.model.GroceryItem
import com.instacart.caper.designtools.data.provider.ItemProvider
import com.instacart.caper.designtools.ui.theme.OutlineColor
import com.instacart.caper.designtools.ui.outlinedBorder

@Composable
fun ProduceItemCard(
    item: GroceryItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val imageBitmap = remember(item.imagePath) {
        try {
            val inputStream = context.assets.open(item.imagePath)
            BitmapFactory.decodeStream(inputStream)?.asImageBitmap()
        } catch (e: Exception) {
            null
        }
    }

    Column(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .outlinedBorder()
            .clickable(onClick = onClick)
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        // Item image
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap,
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(120.dp),
                contentScale = ContentScale.Fit
            )
        } else {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = item.title,
                tint = Color(0xFFE0E0E0),
                modifier = Modifier
                    .fillMaxWidth()
                    .size(120.dp)
            )
        }

        // Item title
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            minLines = 2,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Item barcode (showing last 4 digits for display)
        Text(
            text = item.barcode.takeLast(4),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 240)
@Composable
fun ProduceItemCardPreview() {
    MaterialTheme {
        ProduceItemCard(
            item = ItemProvider.getAllProduceItems()[0], // Red Delicious Apples
        )
    }
}
