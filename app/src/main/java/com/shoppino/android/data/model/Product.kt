package com.shoppino.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Entity(tableName = "products")
@Parcelize
data class Product(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String,
    val img: String,
    val rating: Int,
    val stock: Int,
    val price: Double,
    val category: String,
    val deleted: Boolean = false,
    val isLiked: Boolean = false
) : Parcelable
