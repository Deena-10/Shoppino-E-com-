package com.shoppino.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppino.android.data.model.*
import com.shoppino.android.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        checkAuthStatus()
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                val response = authRepository.login(email, password)
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    _authState.value = AuthState(
                        isAuthenticated = true,
                        user = loginResponse?.user,
                        accessToken = loginResponse?.accessToken,
                        refreshToken = loginResponse?.refreshToken
                    )
                } else {
                    _error.value = "Login failed: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Login failed"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                val response = authRepository.register(name, email, password, confirmPassword)
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    _authState.value = AuthState(
                        isAuthenticated = registerResponse?.accessToken != null,
                        user = registerResponse?.user,
                        accessToken = registerResponse?.accessToken
                    )
                } else {
                    _error.value = "Registration failed: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Registration failed"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun googleSignIn(idToken: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                val response = authRepository.googleOAuth(idToken)
                if (response.isSuccessful) {
                    val oauthResponse = response.body()
                    _authState.value = AuthState(
                        isAuthenticated = true,
                        user = oauthResponse?.user,
                        accessToken = oauthResponse?.accessToken
                    )
                } else {
                    _error.value = "Google sign-in failed: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Google sign-in failed"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                val response = authRepository.forgotPassword(email)
                if (response.isSuccessful) {
                    // Handle success (show OTP screen)
                } else {
                    _error.value = "Failed to send reset code: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to send reset code"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun verifyOtp(email: String, otp: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                val response = authRepository.verifyOtp(email, otp)
                if (response.isSuccessful) {
                    val otpResponse = response.body()
                    _authState.value = AuthState(
                        isAuthenticated = otpResponse?.accessToken != null,
                        user = _authState.value.user,
                        accessToken = otpResponse?.accessToken
                    )
                } else {
                    _error.value = "OTP verification failed: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "OTP verification failed"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _authState.value = AuthState()
        }
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            val isAuthenticated = authRepository.isAuthenticated()
            if (isAuthenticated) {
                val user = authRepository.getCurrentUser()
                val token = authRepository.getAccessToken()
                _authState.value = AuthState(
                    isAuthenticated = true,
                    user = user,
                    accessToken = token
                )
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
    
    fun mockGoogleSignIn() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                // Simulate Google OAuth with mock data
                val mockUser = User(
                    id = 2L,
                    name = "Google User",
                    email = "user@gmail.com",
                    phoneNumber = "+1234567890",
                    address = "123 Google Street, Google City",
                    role = "USER"
                )
                
                _authState.value = AuthState(
                    isAuthenticated = true,
                    user = mockUser,
                    accessToken = "mock_google_token_${System.currentTimeMillis()}"
                )
            } catch (e: Exception) {
                _error.value = e.message ?: "Google sign-in failed"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun updateUserProfile(name: String, email: String, phoneNumber: String, address: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                val response = authRepository.updateUserProfile(name, email, phoneNumber, address)
                if (response.isSuccessful) {
                    val updatedUser = response.body()
                    _authState.value = _authState.value.copy(user = updatedUser)
                } else {
                    _error.value = "Profile update failed: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Profile update failed"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
