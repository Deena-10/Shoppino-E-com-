package com.shoppino.android.data.repository

import com.shoppino.android.data.api.ApiService
import com.shoppino.android.data.model.*
import com.shoppino.android.security.JwtService
import retrofit2.Response

class AuthRepository(
    private val apiService: ApiService
) {
    
    suspend fun login(email: String, password: String): Response<LoginResponse> {
        val loginRequest = LoginRequest(email, password)
        val response = apiService.login(loginRequest)
        
        if (response.isSuccessful) {
            response.body()?.let { loginResponse ->
                // Save tokens and user info
                JwtService.saveTokens(loginResponse.accessToken, loginResponse.refreshToken)
                JwtService.saveUserInfo(loginResponse.user.email, loginResponse.user.role)
            }
        }
        
        return response
    }
    
    suspend fun register(name: String, email: String, password: String): Response<RegisterResponse> {
        val registerRequest = RegisterRequest(name, email, password)
        val response = apiService.register(registerRequest)
        
        if (response.isSuccessful) {
            response.body()?.let { registerResponse ->
                // Save tokens if provided
                registerResponse.accessToken?.let { token ->
                    JwtService.saveTokens(token, null)
                    JwtService.saveUserInfo(registerResponse.user.email, registerResponse.user.role)
                }
            }
        }
        
        return response
    }
    
    suspend fun googleOAuth(idToken: String): Response<OAuth2Response> {
        val googleRequest = GoogleOAuthRequest(idToken)
        val response = apiService.googleOAuth(googleRequest)
        
        if (response.isSuccessful) {
            response.body()?.let { oauthResponse ->
                // Save tokens and user info
                JwtService.saveTokens(oauthResponse.accessToken, null)
                JwtService.saveUserInfo(oauthResponse.user.email, oauthResponse.user.role)
            }
        }
        
        return response
    }
    
    suspend fun forgotPassword(email: String): Response<Map<String, String>> {
        val request = PasswordResetRequest(email)
        return apiService.forgotPassword(request)
    }
    
    suspend fun verifyOtp(email: String, otp: String): Response<OtpVerificationResponse> {
        val request = OtpVerificationRequest(email, otp)
        val response = apiService.verifyOtp(request)
        
        if (response.isSuccessful) {
            response.body()?.let { otpResponse ->
                // Save token if provided
                otpResponse.accessToken?.let { token ->
                    JwtService.saveTokens(token, null)
                }
            }
        }
        
        return response
    }
    
    suspend fun refreshToken(): Response<LoginResponse>? {
        val refreshToken = JwtService.getRefreshToken()
        return if (refreshToken != null) {
            val response = apiService.refreshToken("Bearer $refreshToken")
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    // Update tokens
                    JwtService.saveTokens(loginResponse.accessToken, loginResponse.refreshToken)
                }
            }
            response
        } else null
    }
    
    fun logout() {
        JwtService.clearTokens()
    }
    
    fun isAuthenticated(): Boolean {
        return JwtService.isAuthenticated()
    }
    
    fun isAdmin(): Boolean {
        return JwtService.isAdmin()
    }
    
    fun getCurrentUserEmail(): String? {
        return JwtService.getUserEmail()
    }
}
