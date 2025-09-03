package com.shoppino.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey val id: Long,  // Match Long type from Spring Boot
    val name: String,
    val description: String,
    val img: String,  // Image URL from your database
    val rating: Int,
    val stock: Int,
    val price: Double,  // Match double type from Spring Boot
    val category: String,
    val deleted: Boolean = false  // Match boolean type from Spring Boot
)
