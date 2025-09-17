package com.shoppino.android.data.repository

import com.shoppino.android.data.api.ApiService
import com.shoppino.android.data.api.OrderItemRequest
import com.shoppino.android.data.api.OrderDTO
import com.shoppino.android.data.local.OrderDao
import com.shoppino.android.data.model.Order
import com.shoppino.android.data.model.OrderItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val apiService: ApiService,
    private val orderDao: OrderDao
) {

    suspend fun placeOrder(orderItems: List<OrderItemRequest>): retrofit2.Response<String> {
        return try {
            val response = apiService.placeOrder(orderItems)
            if (response.isSuccessful) {
                // Store order locally for offline access
                // Note: You would need to parse the response to get order details
                // For now, we'll just return the response
            }
            response
        } catch (e: Exception) {
            // Handle network error
            throw e
        }
    }

    suspend fun getMyOrders(): List<OrderDTO> {
        return try {
            val response = apiService.getMyOrders()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                // Fallback to local data
                getLocalOrders()
            }
        } catch (e: Exception) {
            // Fallback to local data
            getLocalOrders()
        }
    }

    suspend fun getOrderById(orderId: Long): OrderDTO? {
        return try {
            val response = apiService.getOrderById(orderId)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getAllOrders(): List<OrderDTO> {
        return try {
            val response = apiService.getAllOrders()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun getLocalOrders(): List<OrderDTO> {
        // Convert local orders to DTOs
        // This is a simplified version
        return emptyList()
    }

    fun getOrdersFlow(): Flow<List<Order>> {
        return orderDao.getAllOrders()
    }

    suspend fun insertOrder(order: Order) {
        orderDao.insertOrder(order)
    }

    suspend fun insertOrderItem(orderItem: OrderItem) {
        orderDao.insertOrderItem(orderItem)
    }
}
