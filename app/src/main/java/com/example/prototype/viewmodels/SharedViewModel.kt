package com.example.prototype.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.State

class SharedViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<String>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    private val _isPrimaryActive = mutableStateOf(true)
    val isPrimaryActive: State<Boolean> = _isPrimaryActive

    fun toggleActiveScreen() {
        _isPrimaryActive.value = !_isPrimaryActive.value
    }

    fun addItem(item: String) {
        _cartItems.value += item
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}