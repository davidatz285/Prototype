package com.example.prototype.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prototype.viewmodels.SharedViewModel
import kotlinx.coroutines.launch

@Composable
fun CustomerScreen(viewModel: SharedViewModel) {
    val scope = rememberCoroutineScope()

    Column(Modifier.padding(16.dp)) {
        Text("Welcome Customer")
        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            scope.launch {
                viewModel.addItem("Item A")
            }
        }) {
            Text("Add Item A")
        }
    }
}

