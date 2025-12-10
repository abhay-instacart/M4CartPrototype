package com.instacart.caper.designtools.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.instacart.caper.designtools.R
import com.instacart.caper.designtools.data.model.GroceryItem
import com.instacart.caper.designtools.data.provider.ItemProvider
import com.instacart.caper.designtools.ui.outlinedBorder
import com.instacart.caper.designtools.ui.theme.HighlightColor
import com.instacart.caper.designtools.ui.theme.OutlineColor
import java.util.Locale

@Composable
fun CatalogItemCard(
    item: GroceryItem,
    context: android.content.Context,
    onClick: () -> Unit = {}
) {
    val imageBitmap = remember(item.imagePath) {
        try {
            val inputStream = context.assets.open(item.imagePath)
            BitmapFactory.decodeStream(inputStream)?.asImageBitmap()
        } catch (e: Exception) {
            null
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .outlinedBorder()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(20.dp)
    ) {
        // Item image
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap,
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit
            )
        } else {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = item.title,
                tint = Color(0xFFE0E0E0),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Price
        Text(
            text = "$${String.format(Locale.US, "%.2f", item.price)}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.padding(vertical = 2.dp))

        // Discount
        Text(
            text = "$1 off",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .background(
                    color = HighlightColor,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(all = 4.dp)
        )

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        // Title (with min lines of 2)
        Text(
            text = item.title,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            minLines = 2,
            maxLines = 2
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Column {
            // See eligible items button
            Button(
                onClick = { /* Handle click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE8E8E8)
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "See eligible items",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            // Clip coupon button
            OutlinedButton(
                onClick = { /* Handle click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = Color.Black
                ),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ids_icon_coupon),
                        contentDescription = "Clip coupon",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Clip coupon",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 280, heightDp = 416)
@Composable
fun CatalogItemCardPreview() {
    val context = LocalContext.current
    MaterialTheme {
        CatalogItemCard(
            item = ItemProvider.getAllCatalogItems()[6], // Ice Cream
            context = context
        )
    }
}
