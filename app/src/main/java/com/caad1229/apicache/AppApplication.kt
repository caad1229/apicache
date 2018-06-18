package com.caad1229.apicache

import android.app.Application
import com.caad1229.apicache.di.component.AppApplicationComponent
import com.caad1229.apicache.di.component.DaggerAppApplicationComponent
import com.caad1229.apicache.di.module.AppApplicationModule

class AppApplication : Application() {

    lateinit var component: AppApplicationComponent

    override fun onCreate() {
        component = DaggerAppApplicationComponent.builder()
                .appApplicationModule(AppApplicationModule(this))
                .build()
        component.inject(this)

        super.onCreate()

    }
}