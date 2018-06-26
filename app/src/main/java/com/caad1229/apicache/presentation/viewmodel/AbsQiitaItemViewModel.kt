package com.caad1229.apicache.presentation.viewmodel

import android.util.Log
import com.caad1229.apicache.presentation.entity.QiitaItem
import com.caad1229.apicache.presentation.ui.QiitaItemsAdapter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

abstract class AbsQiitaItemViewModel {
    var adapter: QiitaItemsAdapter? = null

    private val disposeBag: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    fun onStart() {
        fetchData()
    }

    fun fetchData(forceRemote: Boolean = false) {
        useCaseRepository(forceRemote)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter?.updateData(it)
                }, {
                    Log.d("TESTTEST", "errorrrr")
                })
                .addTo(disposeBag)
    }

    fun onStop() {
        disposeBag.dispose()
    }

    abstract fun useCaseRepository(forceRemote: Boolean): Single<List<QiitaItem>>
}