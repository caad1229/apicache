package com.caad1229.apicache.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppApplicationModule(val app: Application) {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .build()
    }

    companion object {
        private const val CONNECT_TIMEOUT_MILLIS: Long = 15 * 1000
        private const val READ_TIMEOUT_MILLIS: Long = 60 * 1000
    }
}