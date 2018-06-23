package com.caad1229.apicache

import android.app.Application
import com.caad1229.apicache.di.component.AppApplicationComponent
import com.caad1229.apicache.di.component.DaggerAppApplicationComponent
import com.caad1229.apicache.di.module.AppApplicationModule
import com.facebook.stetho.Stetho
import okhttp3.OkHttpClient
import javax.inject.Inject

class AppApplication : Application() {

    lateinit var component: AppApplicationComponent
    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun onCreate() {
        component = DaggerAppApplicationComponent.builder()
                .appApplicationModule(AppApplicationModule(this))
                .build()
        component.inject(this)

        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }
}