package com.caad1229.apicache.di.component

import com.caad1229.apicache.AppApplication
import com.caad1229.apicache.di.module.ActivityModule
import com.caad1229.apicache.di.module.AppApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppApplicationModule::class)])
interface AppApplicationComponent {
    fun createActivityComponent(activityModule: ActivityModule): ActivityComponent

    fun inject(target: AppApplication)
}