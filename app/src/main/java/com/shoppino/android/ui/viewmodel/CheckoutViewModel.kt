package com.shoppino.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppino.android.data.model.CartItemDto
import com.shoppino.android.data.repository.CartRepository
import com.shoppino.android.data.repository.OrderRepository
import com.shoppino.android.payment.RazorpayManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val razorpayManager: RazorpayManager
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItemDto>>(emptyList())
    val cartItems: StateFlow<List<CartItemDto>> = _cartItems.asStateFlow()

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isPaymentCompleted = MutableStateFlow(false)
    val isPaymentCompleted: StateFlow<Boolean> = _isPaymentCompleted.asStateFlow()

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            try {
                val items = cartRepository.getCartItems()
                _cartItems.value = items
                calculateTotalPrice(items)
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load cart items"
            }
        }
    }

    private fun calculateTotalPrice(items: List<CartItemDto>) {
        val total = items.sumOf { it.price * it.quantity }
        _totalPrice.value = total
    }

    fun proceedToPayment(
        customerName: String,
        customerEmail: String,
        customerPhone: String,
        deliveryAddress: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                // Create order items from cart
                val orderItems = _cartItems.value.map { cartItem ->
                    com.shoppino.android.data.api.OrderItemRequest(
                        productId = cartItem.productId,
                        quantity = cartItem.quantity
                    )
                }
                
                // Place order
                val orderResponse = orderRepository.placeOrder(orderItems)
                
                if (orderResponse.isSuccessful) {
                    // Clear cart after successful order
                    cartRepository.clearCart()
                    _isPaymentCompleted.value = true
                } else {
                    _error.value = "Failed to place order: ${orderResponse.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to process payment"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
