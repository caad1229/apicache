package com.caad1229.apicache.di.module

import android.app.Activity
import com.caad1229.apicache.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @ActivityScope
    fun provideActivityContext(): Activity = activity
}