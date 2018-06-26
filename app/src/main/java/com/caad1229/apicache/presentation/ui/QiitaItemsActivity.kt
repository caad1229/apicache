package com.caad1229.apicache.presentation.ui

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    private var forceRemote: Boolean = false

    @Inject
    lateinit var userItemsViewModel: QiitaUserItemsViewModel
    @Inject
    lateinit var recentlyItemsViewModel: QiitaRecentlyItemsViewModel

    private lateinit var viewModel: AbsQiitaItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            forceRemote = savedInstanceState.getBoolean(SAVE_INSTANCE_STATE_FORCE_REMOTE)
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_qiita_user_item)
        binding.recyclerView.adapter = adapter
        binding.swipe.setOnRefreshListener {
            viewModel.fetchData(forceRemote)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // userId がある場合はユーザの一覧を表示する
        viewModel = if (intent.getStringExtra(EXTRA_USER_ID) == null) {
            supportActionBar?.title = resources.getString(R.string.qiita_recently_items_title)
            recentlyItemsViewModel
        } else {
            val userId = intent.getStringExtra(EXTRA_USER_ID)
            supportActionBar?.title = resources.getString(R.string.qiita_user_items_title, userId)
            userItemsViewModel.userName = userId
            userItemsViewModel
        }
        viewModel.handler = this
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVE_INSTANCE_STATE_FORCE_REMOTE, forceRemote)
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onStop() {
        viewModel.onStop()
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cache, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_force_remote -> {
                item.isChecked = !item.isChecked
                forceRemote = item.isChecked
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
        private const val SAVE_INSTANCE_STATE_FORCE_REMOTE = BuildConfig.APPLICATION_ID + ".use_cache"

        fun createIntent(context: Context, userId: String? = null): Intent {
            val intent = Intent(context, QiitaItemsActivity::class.java)
            intent.putExtra(EXTRA_USER_ID, userId)
            return intent
        }
    }
}