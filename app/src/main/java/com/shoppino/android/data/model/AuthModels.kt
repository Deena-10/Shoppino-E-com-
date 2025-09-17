package com.shoppino.android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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
    val confirmPassword: String,
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

data class PasswordResetConfirmRequest(
    val email: String,
    val otp: String,
    val newPassword: String
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

data class RefreshTokenRequest(
    val refreshToken: String
)

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)

@Parcelize
data class AuthState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null
) : Parcelable
