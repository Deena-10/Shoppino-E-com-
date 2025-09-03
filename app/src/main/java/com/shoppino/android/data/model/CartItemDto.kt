package com.shoppino.android.data.model

data class CartItemDto(
    val productId: Long,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val img: String,
    val rating: Int,
    val stock: Int
)
