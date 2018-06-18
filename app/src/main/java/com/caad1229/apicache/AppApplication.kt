package com.caad1229.apicache

import android.app.Application
import com.caad1229.apicache.di.component.DaggerAppApplicationComponent
import com.caad1229.apicache.di.module.AppApplicationModule

class AppApplication : Application() {

    override fun onCreate() {
        DaggerAppApplicationComponent.builder()
                .appApplicationModule(AppApplicationModule(this))
                .build()
                .inject(this)

        super.onCreate()

    }
}