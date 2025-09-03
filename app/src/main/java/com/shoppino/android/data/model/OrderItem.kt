package com.shoppino.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_item")
data class OrderItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productName: String,
    val quantity: Int,
    val price: Double,
    val orderId: Long,
    val productId: Long? = null
)
