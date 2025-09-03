package com.shoppino.android.data.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String?,
    val user: User,
    val message: String? = null
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String = "USER"
)

data class RegisterResponse(
    val message: String,
    val user: User,
    val accessToken: String? = null
)

data class GoogleOAuthRequest(
    val idToken: String
)

data class OAuth2Response(
    val accessToken: String,
    val user: User
)

data class PasswordResetRequest(
    val email: String
)

data class OtpVerificationRequest(
    val email: String,
    val otp: String
)

data class OtpVerificationResponse(
    val success: Boolean,
    val message: String,
    val accessToken: String? = null
)
