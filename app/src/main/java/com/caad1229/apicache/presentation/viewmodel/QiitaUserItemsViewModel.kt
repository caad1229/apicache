package com.caad1229.apicache.presentation.viewmodel

import android.util.Log
import com.caad1229.apicache.data.repository.QiitaRepository
import com.caad1229.apicache.presentation.ui.QiitaUserItemsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class QiitaUserItemsViewModel @Inject constructor(
        private val repository: QiitaRepository
) {
    var userName = "caad1229"
    var adapter: QiitaUserItemsAdapter? = null

    private val disposeBag: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    fun onStart() {
        repository.getItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter?.updateData(it)
                }, {
                    // TODO: handle error
                    Log.d("TESTTEST", "errorrrr")
                })
                .addTo(disposeBag)
    }

    fun onStop() {
        disposeBag.dispose()
    }
}