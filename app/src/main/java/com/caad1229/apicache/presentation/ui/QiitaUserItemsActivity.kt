package com.caad1229.apicache.presentation.ui

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.caad1229.apicache.R
import com.caad1229.apicache.databinding.ActivityQiitaUserItemBinding
import com.caad1229.apicache.di.component.ActivityComponent
import com.caad1229.apicache.presentation.BaseActivity
import com.caad1229.apicache.presentation.entity.QiitaItem
import com.caad1229.apicache.presentation.entity.QiitaUser
import com.caad1229.apicache.presentation.navigator.QiitaNavigator
import com.caad1229.apicache.presentation.viewmodel.QiitaUserItemsViewModel
import javax.inject.Inject

class QiitaUserItemsActivity : BaseActivity(), QiitaNavigator {

    private lateinit var binding: ActivityQiitaUserItemBinding
    private val adapter: QiitaUserItemsAdapter = QiitaUserItemsAdapter(this)

    @Inject
    lateinit var viewModel: QiitaUserItemsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_qiita_user_item)
        binding.recyclerView.adapter = adapter
        viewModel.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onStop() {
        viewModel.onStop()
        super.onStop()
    }

    override fun navigateToUserDetail(user: QiitaUser) {
        // TODO: show user profile page
    }

    override fun navigateToItemDetail(item: QiitaItem) {
        // TODO: show article page
    }

    override fun inject(component: ActivityComponent) {
        component.inject(this)
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, QiitaUserItemsActivity::class.java)
    }
}