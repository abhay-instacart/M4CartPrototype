package com.instacart.caper.designtools.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.instacart.caper.designtools.ui.extensions.fadingEdges
import com.instacart.caper.designtools.ui.theme.CheckoutGreen

/**
 * Keyboard shortcuts dialog that shows a list of shortcuts for the app.
 *
 * @param onDismiss Callback when the dialog is dismissed
 */
@Composable
fun KeyboardShortcutsDialog(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .width(520.dp)
                .height(520.dp)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 32.dp, horizontal = 48.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title (fixed at top)
                Text(
                    text = "Keyboard Shortcuts",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Scrollable content area (shortcuts sections)
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 4.dp)
                        .fadingEdges(scrollState, fadeLength = 24.dp)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Home Screen Section
                    Text(
                        text = "Home Screen",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DebugMenuRow(
                            key = "A",
                            description = "Add random catalog item"
                        )
                        DebugMenuRow(
                            key = "1",
                            description = "Add Sushi"
                        )
                        DebugMenuRow(
                            key = "2",
                            description = "Add Birthday Cake"
                        )
                        DebugMenuRow(
                            key = "3",
                            description = "Add Charmin Toilet Paper"
                        )
                        DebugMenuRow(
                            key = "4",
                            description = "Add Poland Spring Water"
                        )
                        DebugMenuRow(
                            key = "5",
                            description = "Add Purina One Dog Food"
                        )
                    }

                    // Divider
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp),
                        color = Color.LightGray.copy(alpha = 0.5f)
                    ) {}

                    // Produce Search Screen Section
                    Text(
                        text = "Produce Search Screen (Item Selected)",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DebugMenuRow(
                            key = "W",
                            description = "Randomize weight (1.0 - 10.0 lbs)"
                        )
                        DebugMenuRow(
                            key = "E",
                            description = "Show error dialog (requires weight > 0)"
                        )
                    }
                }

                // Close button (fixed at bottom)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CheckoutGreen
                        ),
                        shape = RoundedCornerShape(48.dp),
                        modifier = Modifier
                            .width(200.dp)
                            .height(56.dp)
                    ) {
                        Text(
                            text = "Close",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DebugMenuRow(
    key: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Key display
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFE0E0E0),
            modifier = Modifier.size(48.dp)
        ) {
            Text(
                text = key,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 32.sp
            )
        }

        // Description
        Text(
            text = description,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(device = "spec:width=540dp,height=540dp")
@Composable
fun KeyboardShortcutsDialogPreview() {
    MaterialTheme {
        KeyboardShortcutsDialog(
            onDismiss = {}
        )
    }
}
