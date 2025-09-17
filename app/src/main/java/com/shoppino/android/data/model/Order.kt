package com.shoppino.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val customerName: String,
    val customerEmail: String,
    val orderDate: Long = System.currentTimeMillis(),
    val totalPrice: Double,
    val userId: Long? = null
)
