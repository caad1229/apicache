package com.caad1229.apicache.presentation.ui

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.caad1229.apicache.BuildConfig
import com.caad1229.apicache.R
import com.caad1229.apicache.databinding.ActivityQiitaUserItemBinding
import com.caad1229.apicache.di.component.ActivityComponent
import com.caad1229.apicache.presentation.BaseActivity
import com.caad1229.apicache.presentation.entity.QiitaItem
import com.caad1229.apicache.presentation.entity.QiitaUser
import com.caad1229.apicache.presentation.navigator.QiitaItemNavigator
import com.caad1229.apicache.presentation.viewmodel.AbsQiitaItemViewModel
import com.caad1229.apicache.presentation.viewmodel.QiitaRecentlyItemsViewModel
import com.caad1229.apicache.presentation.viewmodel.QiitaUserItemsViewModel
import javax.inject.Inject

class QiitaItemsActivity : BaseActivity(), QiitaItemNavigator, AbsQiitaItemViewModel.Handler {

    private lateinit var binding: ActivityQiitaUserItemBinding
    private val adapter: QiitaItemsAdapter = QiitaItemsAdapter(this)

    @Inject
    lateinit var userItemsViewModel: QiitaUserItemsViewModel
    @Inject
    lateinit var recentlyItemsViewModel: QiitaRecentlyItemsViewModel

    lateinit var viewModel: AbsQiitaItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_qiita_user_item)
        binding.recyclerView.adapter = adapter
        binding.swipe.setOnRefreshListener {
            viewModel.fetchData()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // userId がある場合はユーザの一覧を表示する
        viewModel = if (intent.getStringExtra(EXTRA_USER_ID) == null) {
            recentlyItemsViewModel
        } else {
            userItemsViewModel.userName = intent.getStringExtra(EXTRA_USER_ID)
            userItemsViewModel
        }
        viewModel.handler = this
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onStop() {
        viewModel.onStop()
        super.onStop()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onFetchSuccess(data: List<QiitaItem>) {
        binding.swipe.isRefreshing = false
        adapter.updateData(data)
    }

    override fun onFetchError(error: Throwable) {
        binding.swipe.isRefreshing = false
        // TODO: handle error
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
        private const val EXTRA_USER_ID = BuildConfig.APPLICATION_ID + ".user_id"

        fun createIntent(context: Context, userId: String? = null): Intent {
            val intent = Intent(context, QiitaItemsActivity::class.java)
            intent.putExtra(EXTRA_USER_ID, userId)
            return intent
        }
    }
}