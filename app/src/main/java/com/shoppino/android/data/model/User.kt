package com.shoppino.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class User(
    @PrimaryKey val id: Long,
    val name: String,
    val email: String,
    val password: String = "",
    val role: String = "USER",
    val address: String? = null,
    val phoneNumber: String? = null,
    val refreshToken: String? = null,
    val verified: Boolean = false,
    val verificationCode: String? = null,
    val otpExpiryTime: Long? = null
) : Parcelable
