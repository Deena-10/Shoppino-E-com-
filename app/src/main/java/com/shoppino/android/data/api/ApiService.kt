package com.shoppino.android.data.api

import com.shoppino.android.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
    
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>
    
    @POST("auth/google")
    suspend fun googleOAuth(@Body googleRequest: GoogleOAuthRequest): Response<OAuth2Response>
    
    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body request: PasswordResetRequest): Response<Map<String, String>>
    
    @POST("auth/verify-otp")
    suspend fun verifyOtp(@Body request: OtpVerificationRequest): Response<OtpVerificationResponse>
    
    @POST("auth/refresh-token")
    suspend fun refreshToken(@Header("Authorization") refreshToken: String): Response<LoginResponse>
    
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>
    
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Long): Response<Product>
    
    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): Response<List<Product>>
    
    @GET("products/search")
    suspend fun searchProducts(@Query("query") query: String): Response<List<Product>>
    
    @GET("products/search-suggestions")
    suspend fun getSearchSuggestions(@Query("query") query: String): Response<List<String>>
    
    @GET("cart")
    suspend fun getCartItems(): Response<List<CartItemDto>>
    
    @POST("cart")
    suspend fun addToCart(@Body cartRequest: CartItemRequest): Response<Void>
    
    @DELETE("cart/{productId}")
    suspend fun removeFromCart(@Path("productId") productId: Long): Response<Void>
    
    @POST("orders/place")
    suspend fun placeOrder(@Body orderItems: List<OrderItemRequest>): Response<String>
    
    @GetMapping("orders/my")
    suspend fun getMyOrders(): Response<List<OrderDTO>>
    
    @GET("user/profile")
    suspend fun getUserProfile(): Response<UserProfileDTO>
    
    @PUT("user/profile")
    suspend fun updateProfile(@Body profile: UserProfileDTO): Response<UserProfileDTO>
    
    @PreAuthorize("hasRole('ADMIN')")
    @POST("products")
    suspend fun addProduct(@Body product: Product): Response<Product>
    
    @PreAuthorize("hasRole('ADMIN')")
    @PUT("products/{id}")
    suspend fun updateProduct(@Path("id") id: Long, @Body product: Product): Response<Product>
    
    @PreAuthorize("hasRole('ADMIN')")
    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Long): Response<String>
    
    @PreAuthorize("hasRole('ADMIN')")
    @GET("orders/all")
    suspend fun getAllOrders(): Response<List<OrderDTO>>
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
