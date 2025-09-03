package com.shoppino.android.data.local

import androidx.room.*
import com.shoppino.android.data.model.OrderItem
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderItemDao {
    
    @Query("SELECT * FROM order_item WHERE orderId = :orderId")
    fun getOrderItemsByOrderId(orderId: Long): Flow<List<OrderItem>>
    
    @Query("SELECT * FROM order_item WHERE id = :id")
    suspend fun getOrderItemById(id: Long): OrderItem?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItem(orderItem: OrderItem)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(orderItems: List<OrderItem>)
    
    @Update
    suspend fun updateOrderItem(orderItem: OrderItem)
    
    @Delete
    suspend fun deleteOrderItem(orderItem: OrderItem)
    
    @Query("DELETE FROM order_item WHERE orderId = :orderId")
    suspend fun deleteOrderItemsByOrderId(orderId: Long)
    
    @Query("DELETE FROM order_item")
    suspend fun deleteAllOrderItems()
}
