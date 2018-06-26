package com.caad1229.apicache.di.component

import com.caad1229.apicache.MainActivity
import com.caad1229.apicache.di.module.ActivityModule
import com.caad1229.apicache.di.scope.ActivityScope
import com.caad1229.apicache.presentation.ui.QiitaItemsActivity
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    fun inject(target: MainActivity)
    fun inject(target: QiitaItemsActivity)
}