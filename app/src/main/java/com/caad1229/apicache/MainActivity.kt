package com.caad1229.apicache

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.caad1229.apicache.databinding.ActivityMainBinding
import com.caad1229.apicache.di.component.ActivityComponent
import com.caad1229.apicache.presentation.BaseActivity
import com.caad1229.apicache.presentation.ui.QiitaItemsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        my_items.setOnClickListener { startActivity(QiitaItemsActivity.createIntent(this, "caad1229")) }
        recently_items.setOnClickListener { startActivity(QiitaItemsActivity.createIntent(this)) }
    }

    override fun inject(component: ActivityComponent) {
        component.inject(this)
    }
}
