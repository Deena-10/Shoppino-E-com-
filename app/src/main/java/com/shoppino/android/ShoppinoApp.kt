package com.shoppino.android

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShoppinoApp : Application() {
    
    companion object {
        lateinit var instance: ShoppinoApp
            private set
    }
    
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    
    fun getAppContext(): Context = applicationContext
}
