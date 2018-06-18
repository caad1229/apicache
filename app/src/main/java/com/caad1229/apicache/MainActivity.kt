package com.caad1229.apicache

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.caad1229.apicache.databinding.ActivityMainBinding
import com.caad1229.apicache.di.component.ActivityComponent
import com.caad1229.apicache.presentation.BaseActivity
import okhttp3.OkHttpClient
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun inject(component: ActivityComponent) {
        component.inject(this)
    }
}
