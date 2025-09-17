package com.shoppino.android.di

import android.content.Context
import com.shoppino.android.auth.GoogleOAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GoogleOAuthModule {

    @Provides
    @Singleton
    fun provideGoogleOAuthService(@ApplicationContext context: Context): GoogleOAuthService {
        return GoogleOAuthService(context)
    }
}
