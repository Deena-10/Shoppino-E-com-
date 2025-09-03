package com.shoppino.android.data.model

// Login Request - matches your LoginRequest.java
data class LoginRequest(
    val email: String,
    val password: String
)

// Login Response - matches your JWT service response
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String?,
    val user: User,
    val message: String? = null
)

// Register Request
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String = "USER"
)

// Register Response
data class RegisterResponse(
    val message: String,
    val user: User,
    val accessToken: String? = null
)

// OAuth2 Google Login Request
data class GoogleOAuthRequest(
    val idToken: String
)

// OAuth2 Response
data class OAuth2Response(
    val accessToken: String,
    val user: User
)

// Password Reset Request
data class PasswordResetRequest(
    val email: String
)

// OTP Verification Request
data class OtpVerificationRequest(
    val email: String,
    val otp: String
)

// OTP Verification Response
data class OtpVerificationResponse(
    val success: Boolean,
    val message: String,
    val accessToken: String? = null
)
