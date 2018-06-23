package com.caad1229.apicache.presentation.ui

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.caad1229.apicache.R
import com.caad1229.apicache.databinding.ActivityQiitaUserItemBinding
import com.caad1229.apicache.di.component.ActivityComponent
import com.caad1229.apicache.presentation.BaseActivity

class QiitaUserItemsActivity : BaseActivity() {
    private lateinit var binding: ActivityQiitaUserItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qiita_user_item)
    }

    override fun inject(component: ActivityComponent) {
        component.inject(this)
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, QiitaUserItemsActivity::class.java)
    }
}