package com.shoppino.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val customerName: String,
    val customerEmail: String,
    val orderDate: Date = Date(),
    val totalPrice: Double,
    val userId: Long? = null
)
