package com.shoppino.android.data.api

import com.shoppino.android.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // Authentication Endpoints
    @POST("api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
    
    @POST("api/auth/signup")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>
    
    @POST("api/auth/firebase")
    suspend fun googleOAuth(@Body googleRequest: GoogleOAuthRequest): Response<OAuth2Response>
    
    @POST("api/auth/forgot-password")
    suspend fun forgotPassword(@Body request: PasswordResetRequest): Response<Map<String, String>>
    
    @POST("api/auth/verify")
    suspend fun verifyOtp(@Body request: OtpVerificationRequest): Response<OtpVerificationResponse>
    
    @POST("api/auth/refresh-token")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<RefreshTokenResponse>
    
    // Product Endpoints
    @GET("api/products")
    suspend fun getProducts(): Response<List<Product>>
    
    @GET("api/products/{id}")
    suspend fun getProductById(@Path("id") id: Long): Response<Product>
    
    @GET("api/products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): Response<List<Product>>
    
    @GET("api/products/search")
    suspend fun searchProducts(@Query("query") query: String): Response<List<Product>>
    
    @GET("api/products/search-suggestions")
    suspend fun getSearchSuggestions(@Query("query") query: String): Response<List<String>>
    
    @POST("api/products/{id}/like")
    suspend fun likeProduct(@Path("id") id: Long): Response<Map<String, String>>
    
    @DELETE("api/products/{id}/like")
    suspend fun unlikeProduct(@Path("id") id: Long): Response<Map<String, String>>
    
    @GET("api/products/{id}/like-status")
    suspend fun getLikeStatus(@Path("id") id: Long): Response<Map<String, Boolean>>
    
    // Cart Endpoints
    @GET("api/cart")
    suspend fun getCartItems(): Response<List<CartItemDto>>
    
    @POST("api/cart/add")
    suspend fun addToCart(@Body cartRequest: CartItemRequest): Response<Void>
    
    @PUT("api/cart/update")
    suspend fun updateCartItem(@Body cartRequest: CartItemRequest): Response<Void>
    
    @DELETE("api/cart/remove")
    suspend fun removeFromCart(@Path("productId") productId: Long): Response<Void>
    
    // Order Endpoints
    @POST("api/orders/place")
    suspend fun placeOrder(@Body orderItems: List<OrderItemRequest>): Response<String>
    
    @GET("api/orders/my")
    suspend fun getMyOrders(): Response<List<OrderDTO>>
    
    @GET("api/orders/{id}")
    suspend fun getOrderById(@Path("id") id: Long): Response<OrderDTO>
    
    // User Profile Endpoints
    @GET("api/user/profile")
    suspend fun getUserProfile(): Response<UserProfileDTO>
    
    @PUT("api/user/profile")
    suspend fun updateProfile(@Body profile: UserProfileDTO): Response<UserProfileDTO>
    
    // Payment Endpoints
    @POST("api/payment/create-order")
    suspend fun createPaymentOrder(@Body request: PaymentOrderRequest): Response<PaymentOrderResponse>
    
    @POST("api/payment/verify")
    suspend fun verifyPayment(@Body request: PaymentVerificationRequest): Response<PaymentVerificationResponse>
    
    @GET("api/payment/test")
    suspend fun testPayment(): Response<Map<String, String>>
    
    @GET("api/payment/env-check")
    suspend fun checkPaymentEnvironment(): Response<Map<String, String>>
    
    // Admin Endpoints
    @POST("api/products")
    suspend fun addProduct(@Body product: Product): Response<Product>
    
    @PUT("api/products/{id}")
    suspend fun updateProduct(@Path("id") id: Long, @Body product: Product): Response<Product>
    
    @DELETE("api/products/{id}")
    suspend fun deleteProduct(@Path("id") id: Long): Response<String>
    
    @GET("api/orders/all")
    suspend fun getAllOrders(): Response<List<OrderDTO>>
    
    // User Profile
    @PUT("api/user/profile")
    suspend fun updateUserProfile(@Body profile: UserProfileDTO): Response<User>
    
    // Server Status
    @GET("api/health")
    suspend fun checkServerStatus(): Response<Map<String, String>>
}

data class OrderItemRequest(
    val productId: Long,
    val quantity: Int
)

data class OrderDTO(
    val id: Long,
    val customerName: String,
    val customerEmail: String,
    val orderDate: String,
    val totalPrice: Double,
    val items: List<OrderItemDTO>
)

data class OrderItemDTO(
    val id: Long,
    val productName: String,
    val quantity: Int,
    val price: Double
)

data class UserProfileDTO(
    val name: String,
    val email: String,
    val phoneNumber: String?,
    val address: String?
)
