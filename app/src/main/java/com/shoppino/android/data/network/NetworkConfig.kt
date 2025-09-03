package com.shoppino.android.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.shoppino.android.data.api.ApiService
import com.shoppino.android.config.DatabaseConfig
import com.shoppino.android.security.JwtService
import java.util.concurrent.TimeUnit

object NetworkConfig {
    
    // Authentication interceptor to add JWT token to requests
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val token = JwtService.getAccessToken()
        
        val newRequest = if (token != null) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }
        
        chain.proceed(newRequest)
    }
    
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(authInterceptor)  // Add JWT token automatically
        .connectTimeout(DatabaseConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(DatabaseConfig.READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(DatabaseConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
        .build()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(DatabaseConfig.API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
