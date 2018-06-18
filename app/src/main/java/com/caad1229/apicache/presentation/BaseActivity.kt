package com.caad1229.apicache.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.caad1229.apicache.AppApplication
import com.caad1229.apicache.di.component.ActivityComponent
import com.caad1229.apicache.di.module.ActivityModule

abstract class BaseActivity : AppCompatActivity() {
    lateinit var component: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        component = (application as AppApplication).component.createActivityComponent(ActivityModule(this))

        inject(component)
        super.onCreate(savedInstanceState)
    }

    abstract fun inject(component: ActivityComponent)
}