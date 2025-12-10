package com.instacart.caper.designtools.ui.components

import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.instacart.caper.designtools.data.model.GroceryItem
import com.instacart.caper.designtools.data.provider.ItemProvider
import com.instacart.caper.designtools.ui.Fire10Preview
import com.instacart.caper.designtools.ui.Fire11Preview
import com.instacart.caper.designtools.ui.extensions.outlinedBorder
import com.instacart.caper.designtools.ui.theme.CheckoutGreen
import com.instacart.caper.designtools.utils.SoundPlayer
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProduceItemBottomSheet(
    modifier: Modifier = Modifier,
    item: GroceryItem,
    title: String = "Place item in cart to weigh",
    onDismiss: () -> Unit,
    onConfirm: (weight: Double, totalPrice: Double) -> Unit = { _, _ -> },
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { false })
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Weight state that can be updated
    var weight by remember { mutableDoubleStateOf(0.0) }
    var displayedPrice by remember { mutableDoubleStateOf(0.0) }
    var showErrorDialog by remember { mutableStateOf(false) }
    val totalPrice = weight * item.price

    // Update displayed price with a delay after weight changes
    LaunchedEffect(weight) {
        kotlinx.coroutines.delay(500)
        displayedPrice = totalPrice
    }

    // Focus requester to ensure the composable can receive key events
    val focusRequester = remember { FocusRequester() }

    // Request focus when the bottom sheet appears
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    ModalBottomSheet(
        onDismissRequest = { },
        sheetState = sheetState,
        containerColor = Color.White,
        dragHandle = null,
        modifier = modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 32.dp)
                .focusRequester(focusRequester)
                .focusable()
                .onPreviewKeyEvent { keyEvent ->
                    // Check if 'W' key is pressed
                    when (keyEvent.key) {
                        Key.W -> {
                            // Randomize weight between 1.0 and 10.0 lbs
                            weight = Random.nextDouble(1.0, 10.0)
                            true // Event handled
                        }

                        Key.E -> {
                            // Show error dialog only if weight and price are not 0
                            if (weight > 0 && displayedPrice > 0) {
                                showErrorDialog = true
                            }
                            true // Event handled
                        }

                        else -> false // Event not handled
                    }
                }
        ) {
            // Header with title and dismiss button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 40.dp)
                        .weight(1f),
                    text = title,
                    textAlign = TextAlign.Center,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                IconButton(onClick = {
                    coroutineScope.launch {
                        sheetState.hide()
                        onDismiss()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dismiss",
                        tint = Color.Black,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Item card with image and details
            ProduceItemCard(item = item)

            Spacer(modifier = Modifier.height(32.dp))

            // Weight and price information
            WeightAndPriceSection(
                weight = weight,
                displayedPrice = displayedPrice,
                onConfirm = { onConfirm(weight, displayedPrice) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Weight range info
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "e-d=0.01 lb • Min: 0.01 lb • Max: 66 lb • Weight: 1.79 lb",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "More discounts may apply on the next screen.",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            // Error dialog
            if (showErrorDialog) {
                // Play error sound when error dialog is shown
                LaunchedEffect(Unit) {
                    SoundPlayer.playError(context)
                }
                ErrorDialog(
                    message = "Unfortunately, the weight did not properly converge. Please weigh again.",
                    onDismiss = {
                        showErrorDialog = false
                        weight = 0.0
                        displayedPrice = 0.0
                    }
                )
            }
        }
    }
}

@Composable
private fun ProduceItemCard(
    item: GroceryItem
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .outlinedBorder()
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Item image
        Box(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.Center
        ) {
            imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = item.title,
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.width(24.dp))

        // Item details
        Column {
            Text(
                text = item.title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.barcode.takeLast(4),
                fontSize = 20.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$%.2f per lb".format(item.price),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun WeightAndPriceSection(
    weight: Double,
    displayedPrice: Double,
    onConfirm: () -> Unit
) {
    var previousWeight by remember { mutableDoubleStateOf(weight) }
    var previousPrice by remember { mutableDoubleStateOf(displayedPrice) }

    val weightGoingToZero = weight == 0.0 && previousWeight > 0.0
    val priceGoingToZero = displayedPrice == 0.0 && previousPrice > 0.0

    // Update previous values after determining direction
    LaunchedEffect(weight) {
        previousWeight = weight
    }
    LaunchedEffect(displayedPrice) {
        previousPrice = displayedPrice
    }

    val isEnabled = weight > 0 && displayedPrice > 0
    val buttonColor by animateColorAsState(
        targetValue = if (isEnabled) CheckoutGreen else Color.LightGray,
        animationSpec = tween(durationMillis = 300),
        label = "button_color_animation"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        // Weight and Total Price
        Row(
            horizontalArrangement = Arrangement.spacedBy(48.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Text(
                    text = "Weight",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
                AnimatedContent(
                    targetState = weight,
                    transitionSpec = {
                        (slideInVertically(
                            animationSpec = tween(300),
                            initialOffsetY = { offset ->
                                if (weightGoingToZero) offset / 2 else -offset / 2
                            }
                        ) + fadeIn(animationSpec = tween(300))).togetherWith(
                            slideOutVertically(
                                animationSpec = tween(300),
                                targetOffsetY = { offset ->
                                    if (weightGoingToZero) -offset / 2 else offset / 2
                                }
                            ) + fadeOut(animationSpec = tween(300))
                        )
                    },
                    label = "weight_animation"
                ) { animatedWeight ->
                    Text(
                        text = "%.2f lb".format(animatedWeight),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            Column {
                Text(
                    text = "Total price",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
                AnimatedContent(
                    targetState = displayedPrice,
                    transitionSpec = {
                        (slideInVertically(
                            animationSpec = tween(300),
                            initialOffsetY = { offset ->
                                if (priceGoingToZero) offset / 2 else -offset / 2
                            }
                        ) + fadeIn(animationSpec = tween(300))).togetherWith(
                            slideOutVertically(
                                animationSpec = tween(300),
                                targetOffsetY = { offset ->
                                    if (priceGoingToZero) -offset / 2 else offset / 2
                                }
                            ) + fadeOut(animationSpec = tween(300))
                        )
                    },
                    label = "price_animation"
                ) { animatedPrice ->
                    Text(
                        text = "$%.2f".format(animatedPrice),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }

        // Confirm button
        Button(
            onClick = onConfirm,
            enabled = isEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                disabledContainerColor = buttonColor
            ),
            shape = RoundedCornerShape(48.dp),
            modifier = Modifier
                .width(360.dp)
                .height(72.dp)
        ) {
            Text(
                text = "Confirm",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}


@Fire10Preview
@Fire11Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProduceItemBottomSheetPreview() {
    MaterialTheme {
        ProduceItemBottomSheet(
            item = ItemProvider.getAllProduceItems()[0],
            onDismiss = {},
            onConfirm = { _, _ -> }
        )
    }
}

