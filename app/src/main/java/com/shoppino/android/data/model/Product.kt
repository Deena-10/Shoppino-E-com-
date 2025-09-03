package com.shoppino.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String,
    val img: String,
    val rating: Int,
    val stock: Int,
    val price: Double,
    val category: String,
    val deleted: Boolean = false
)
