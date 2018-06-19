package com.caad1229.apicache.realm

import android.content.Context
import android.content.Intent
import com.caad1229.apicache.di.component.ActivityComponent
import com.caad1229.apicache.presentation.BaseActivity

class RealmCacheActivity : BaseActivity() {
    override fun inject(component: ActivityComponent) {
        component.inject(this)
    }

    companion object {
        fun createIntent(context: Context): Intent =
                Intent(context, RealmCacheActivity::class.java)
    }
}