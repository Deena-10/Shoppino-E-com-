package com.shoppino.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: Long,  // Match long type from Spring Boot
    val name: String,
    val email: String,
    val password: String,
    val role: String = "USER",  // Default to USER
    val address: String? = null,
    val phoneNumber: String? = null,  // String type from Spring Boot
    val refreshToken: String? = null,
    val verified: Boolean = false,  // Email verification status
    val verificationCode: String? = null,  // 6-digit OTP
    val otpExpiryTime: LocalDateTime? = null  // OTP expiration time
)
