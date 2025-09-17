package com.shoppino.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppino.android.data.model.CartItemDto
import com.shoppino.android.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItemDto>>(emptyList())
    val cartItems: StateFlow<List<CartItemDto>> = _cartItems.asStateFlow()

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadCartItems()
    }

    fun loadCartItems() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                val items = cartRepository.getCartItems()
                _cartItems.value = items
                calculateTotalPrice(items)
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load cart items"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addToCart(productId: Long, quantity: Int = 1) {
        viewModelScope.launch {
            try {
                cartRepository.addToCart(productId, quantity)
                loadCartItems() // Refresh cart
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to add item to cart"
            }
        }
    }

    fun updateCartItemQuantity(productId: Long, newQuantity: Int) {
        viewModelScope.launch {
            try {
                cartRepository.updateCartItemQuantity(productId, newQuantity)
                loadCartItems() // Refresh cart
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to update quantity"
            }
        }
    }

    fun removeFromCart(productId: Long) {
        viewModelScope.launch {
            try {
                cartRepository.removeFromCart(productId)
                loadCartItems() // Refresh cart
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to remove item from cart"
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            try {
                cartRepository.clearCart()
                _cartItems.value = emptyList()
                _totalPrice.value = 0.0
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to clear cart"
            }
        }
    }

    fun refreshCart() {
        loadCartItems()
    }

    private fun calculateTotalPrice(items: List<CartItemDto>) {
        val total = items.sumOf { it.price * it.quantity }
        _totalPrice.value = total
    }

    fun clearError() {
        _error.value = null
    }
}
