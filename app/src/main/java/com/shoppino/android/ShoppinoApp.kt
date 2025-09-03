package com.shoppino.android

import android.app.Application
import android.content.Context
import com.shoppino.android.security.JwtService

class ShoppinoApp : Application() {
    
    companion object {
        lateinit var instance: ShoppinoApp
            private set
    }
    
    override fun onCreate() {
        super.onCreate()
        instance = this
        
        JwtService.init(this)
    }
    
    fun getAppContext(): Context = applicationContext
}
