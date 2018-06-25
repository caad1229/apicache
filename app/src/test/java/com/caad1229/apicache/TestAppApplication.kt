package com.caad1229.apicache

import com.caad1229.apicache.di.component.AppApplicationComponent
import com.caad1229.apicache.di.component.DaggerAppApplicationComponent
import com.caad1229.apicache.di.module.TestAppApplicationModule

// for robolectric
class TestAppApplication: AppApplication() {

    override fun createComponent(): AppApplicationComponent =
        DaggerAppApplicationComponent.builder()
                .appApplicationModule(TestAppApplicationModule(this))
                .build()
}