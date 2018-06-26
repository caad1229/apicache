package com.caad1229.apicache.presentation.viewmodel

import com.caad1229.apicache.presentation.entity.QiitaItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

abstract class AbsQiitaItemViewModel {
    var handler: Handler? = null

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
                    handler?.onFetchSuccess(it)
                }, {
                    handler?.onFetchError(it)
                })
                .addTo(disposeBag)
    }

    fun onStop() {
        disposeBag.dispose()
    }

    abstract fun useCaseRepository(forceRemote: Boolean): Single<List<QiitaItem>>

    interface Handler {
        fun onFetchSuccess(data: List<QiitaItem>)
        fun onFetchError(error: Throwable)
    }
}