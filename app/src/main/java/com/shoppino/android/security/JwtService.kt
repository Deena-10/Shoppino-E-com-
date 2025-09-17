package com.shoppino.android.security

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.shoppino.android.data.model.User
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JwtService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    companion object {
        private const val PREFS_NAME = "shoppino_secure_prefs"
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val USER_KEY = "user"
        private const val FIREBASE_TOKEN_KEY = "firebase_token"
        private const val LAST_SYNC_KEY = "last_sync"
    }
    
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    
    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    private val gson = Gson()
    
    fun saveTokens(accessToken: String, refreshToken: String?) {
        prefs.edit()
            .putString(ACCESS_TOKEN_KEY, accessToken)
            .putString(REFRESH_TOKEN_KEY, refreshToken)
            .apply()
    }
    
    fun getAccessToken(): String? {
        return prefs.getString(ACCESS_TOKEN_KEY, null)
    }
    
    fun getRefreshToken(): String? {
        return prefs.getString(REFRESH_TOKEN_KEY, null)
    }
    
    fun saveUser(user: User) {
        val userJson = gson.toJson(user)
        prefs.edit()
            .putString(USER_KEY, userJson)
            .apply()
    }
    
    fun getUser(): User? {
        val userJson = prefs.getString(USER_KEY, null)
        return if (userJson != null) {
            try {
                gson.fromJson(userJson, User::class.java)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }
    
    fun saveFirebaseToken(token: String) {
        prefs.edit()
            .putString(FIREBASE_TOKEN_KEY, token)
            .apply()
    }
    
    fun getFirebaseToken(): String? {
        return prefs.getString(FIREBASE_TOKEN_KEY, null)
    }
    
    fun saveLastSyncTime(timestamp: Long) {
        prefs.edit()
            .putLong(LAST_SYNC_KEY, timestamp)
            .apply()
    }
    
    fun getLastSyncTime(): Long {
        return prefs.getLong(LAST_SYNC_KEY, 0L)
    }
    
    fun clearTokens() {
        prefs.edit()
            .remove(ACCESS_TOKEN_KEY)
            .remove(REFRESH_TOKEN_KEY)
            .remove(USER_KEY)
            .remove(FIREBASE_TOKEN_KEY)
            .apply()
    }
    
    fun isAuthenticated(): Boolean {
        return getAccessToken() != null
    }
    
    fun isAdmin(): Boolean {
        return getUser()?.role == "ADMIN"
    }
    
    fun isTokenExpired(): Boolean {
        val token = getAccessToken()
        return if (token != null) {
            try {
                // Simple JWT expiration check (in production, use proper JWT library)
                val payload = token.split(".")[1]
                val decoded = String(android.util.Base64.decode(payload, android.util.Base64.URL_SAFE))
                val json = gson.fromJson(decoded, Map::class.java)
                val exp = json["exp"] as? Number
                exp?.let { 
                    val expirationTime = it.toLong() * 1000 // Convert to milliseconds
                    System.currentTimeMillis() > expirationTime
                } ?: true
            } catch (e: Exception) {
                true
            }
        } else {
            true
        }
    }
}
