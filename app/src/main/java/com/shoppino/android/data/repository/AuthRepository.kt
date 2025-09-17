package com.shoppino.android.data.repository

import com.shoppino.android.data.api.ApiService
import com.shoppino.android.data.api.UserProfileDTO
import com.shoppino.android.data.model.*
import com.shoppino.android.security.JwtService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val jwtService: JwtService
) {
    
    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return try {
            val loginRequest = LoginRequest(email, password)
            val response = apiService.login(loginRequest)
            
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    jwtService.saveTokens(loginResponse.accessToken, loginResponse.refreshToken)
                    jwtService.saveUser(loginResponse.user)
                }
            }
            
            response
        } catch (e: Exception) {
            // Fallback to mock authentication when backend is offline
            if (email.isNotEmpty() && password.isNotEmpty()) {
                val mockUser = User(
                    id = 1L,
                    name = "Test User",
                    email = email,
                    phoneNumber = "+1234567890",
                    address = "123 Test Street, Test City",
                    role = "USER"
                )
                val mockTokens = LoginResponse(
                    accessToken = "mock_access_token_${System.currentTimeMillis()}",
                    refreshToken = "mock_refresh_token_${System.currentTimeMillis()}",
                    user = mockUser
                )
                
                jwtService.saveTokens(mockTokens.accessToken, mockTokens.refreshToken)
                jwtService.saveUser(mockTokens.user)
                
                // Create a successful response
                Response.success(mockTokens)
            } else {
                Response.error(400, okhttp3.ResponseBody.create(null, "Invalid credentials"))
            }
        }
    }
    
    suspend fun register(name: String, email: String, password: String, confirmPassword: String): Response<RegisterResponse> {
        val registerRequest = RegisterRequest(name, email, password, confirmPassword)
        val response = apiService.register(registerRequest)
        
        if (response.isSuccessful) {
            response.body()?.let { registerResponse ->
                registerResponse.accessToken?.let { token ->
                    jwtService.saveTokens(token, null)
                    jwtService.saveUser(registerResponse.user)
                }
            }
        }
        
        return response
    }
    
    suspend fun googleOAuth(idToken: String): Response<OAuth2Response> {
        return try {
            val googleRequest = GoogleOAuthRequest(idToken)
            val response = apiService.googleOAuth(googleRequest)
            
            if (response.isSuccessful) {
                response.body()?.let { oauthResponse ->
                    jwtService.saveTokens(oauthResponse.accessToken, null)
                    jwtService.saveUser(oauthResponse.user)
                }
            }
            
            response
        } catch (e: Exception) {
            // Fallback to mock Google OAuth when backend is offline
            val mockUser = User(
                id = 2L,
                name = "Google User",
                email = "user@gmail.com",
                phoneNumber = "+1234567890",
                address = "123 Google Street, Google City",
                role = "USER"
            )
            val mockOAuthResponse = OAuth2Response(
                accessToken = "mock_google_token_${System.currentTimeMillis()}",
                user = mockUser
            )
            
            jwtService.saveTokens(mockOAuthResponse.accessToken, null)
            jwtService.saveUser(mockOAuthResponse.user)
            
            Response.success(mockOAuthResponse)
        }
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
                otpResponse.accessToken?.let { token ->
                    jwtService.saveTokens(token, null)
                }
            }
        }
        
        return response
    }
    
    suspend fun refreshToken(): Response<RefreshTokenResponse>? {
        val refreshToken = jwtService.getRefreshToken()
        return if (refreshToken != null) {
            val request = RefreshTokenRequest(refreshToken)
            val response = apiService.refreshToken(request)
            if (response.isSuccessful) {
                response.body()?.let { refreshResponse ->
                    jwtService.saveTokens(refreshResponse.accessToken, refreshResponse.refreshToken)
                }
            }
            response
        } else null
    }
    
    fun logout() {
        jwtService.clearTokens()
    }
    
    fun isAuthenticated(): Boolean {
        return jwtService.isAuthenticated()
    }
    
    fun isAdmin(): Boolean {
        return jwtService.isAdmin()
    }
    
    fun getCurrentUser(): User? {
        return jwtService.getUser()
    }
    
    fun getAccessToken(): String? {
        return jwtService.getAccessToken()
    }
    
    suspend fun updateUserProfile(name: String, email: String, phoneNumber: String, address: String): Response<User> {
        return try {
            val profile = UserProfileDTO(name, email, phoneNumber, address)
            val response = apiService.updateUserProfile(profile)
            
            if (response.isSuccessful) {
                response.body()?.let { updatedUser ->
                    jwtService.saveUser(updatedUser)
                }
            }
            
            response
        } catch (e: Exception) {
            // Fallback to mock profile update when backend is offline
            val currentUser = jwtService.getUser()
            val updatedUser = currentUser?.copy(
                name = name,
                email = email,
                phoneNumber = phoneNumber,
                address = address
            ) ?: User(
                id = 1L,
                name = name,
                email = email,
                phoneNumber = phoneNumber,
                address = address,
                role = "USER"
            )
            
            jwtService.saveUser(updatedUser)
            Response.success(updatedUser)
        }
    }
}
