package com.shoppino.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")  // Match your Spring Boot @Table name
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,  // Match Long type from Spring Boot
    val productId: Long,  // Match Long type from Spring Boot
    val quantity: Int = 1
)
