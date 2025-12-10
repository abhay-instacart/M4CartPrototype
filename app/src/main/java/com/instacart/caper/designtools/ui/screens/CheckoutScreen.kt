package com.instacart.caper.designtools.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.instacart.caper.designtools.ui.Fire10Preview
import com.instacart.caper.designtools.ui.Fire11Preview

@Composable
fun CheckoutScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Checkout Screen",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 48.sp
        )
    }
}

@Fire10Preview
@Fire11Preview
@Composable
fun CheckoutScreenPreview() {
    MaterialTheme {
        CheckoutScreen()
    }
}
