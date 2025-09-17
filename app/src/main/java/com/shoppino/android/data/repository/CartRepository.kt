package com.shoppino.android.data.repository

import com.shoppino.android.data.api.ApiService
import com.shoppino.android.data.model.CartItemDto
import com.shoppino.android.data.model.CartItemRequest
import com.shoppino.android.data.local.CartDao
import com.shoppino.android.data.model.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    private val apiService: ApiService,
    private val cartDao: CartDao
) {

    suspend fun getCartItems(): List<CartItemDto> {
        return try {
            val response = apiService.getCartItems()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                // Fallback to local data if API fails
                getLocalCartItems()
            }
        } catch (e: Exception) {
            // Fallback to local data if network fails
            getLocalCartItems()
        }
    }

    suspend fun addToCart(productId: Long, quantity: Int) {
        try {
            val cartRequest = CartItemRequest(productId, quantity)
            val response = apiService.addToCart(cartRequest)
            if (response.isSuccessful) {
                // Also update local database
                val cartItem = CartItem(
                    id = 0, // Will be auto-generated
                    productId = productId,
                    quantity = quantity
                )
                cartDao.insertCartItem(cartItem)
            }
        } catch (e: Exception) {
            // Store locally if API fails
            val cartItem = CartItem(
                id = 0,
                productId = productId,
                quantity = quantity
            )
            cartDao.insertCartItem(cartItem)
        }
    }

    suspend fun updateCartItemQuantity(productId: Long, newQuantity: Int) {
        try {
            val cartRequest = CartItemRequest(productId, newQuantity)
            val response = apiService.updateCartItem(cartRequest)
            if (response.isSuccessful) {
                // Also update local database
                cartDao.updateCartItemQuantity(productId, newQuantity)
            }
        } catch (e: Exception) {
            // Update locally if API fails
            cartDao.updateCartItemQuantity(productId, newQuantity)
        }
    }

    suspend fun removeFromCart(productId: Long) {
        try {
            val response = apiService.removeFromCart(productId)
            if (response.isSuccessful) {
                // Also remove from local database
                cartDao.deleteCartItem(productId)
            }
        } catch (e: Exception) {
            // Remove locally if API fails
            cartDao.deleteCartItem(productId)
        }
    }

    suspend fun clearCart() {
        try {
            // Clear local cart
            cartDao.clearCart()
            // Note: You might want to add an API endpoint to clear cart on server
        } catch (e: Exception) {
            // Handle error
        }
    }

    private suspend fun getLocalCartItems(): List<CartItemDto> {
        // This is a simplified version - in a real app, you'd need to join with product data
        return emptyList()
    }

    fun getCartItemsFlow(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems()
    }
}
