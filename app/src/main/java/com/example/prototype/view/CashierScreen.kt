package com.example.prototype.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.prototype.viewmodels.SharedViewModel

@Composable
fun CashierScreen(viewModel: SharedViewModel) {
    val cartItems by viewModel.cartItems.collectAsState()

    Column(Modifier.padding(16.dp)) {
        Text("Cashier View")
        Spacer(Modifier.height(8.dp))

        if (cartItems.isEmpty()) {
            Text("Cart is empty.")
        } else {
            cartItems.forEach { item ->
                Text("Item: $item")
            }
        }

        Spacer(Modifier.height(16.dp))
        Button(onClick = { viewModel.clearCart() }) {
            Text("Clear Cart")
        }
    }
}

@Composable
fun ScreenWithControlBase(
    viewModel: SharedViewModel,
    isPrimary: Boolean,
    content: @Composable () -> Unit
) {
    val isPrimaryActive by viewModel.isPrimaryActive

    if (isPrimary == isPrimaryActive) {
        // Active screen shows its real content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Top bar with switch button (optional)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = { viewModel.toggleActiveScreen() }) {
                    Text("Switch Control")
                }
            }

            // Custom content goes here
            Column(modifier = Modifier.fillMaxSize()) {
                content()
            }
        }
    } else {
        // Inactive screen UI (optional fade or block)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray.copy(alpha = 0.6f)),
        ) {
            content()
            Text(
                text = "Inactive Screen",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
            )

        }
    }
}