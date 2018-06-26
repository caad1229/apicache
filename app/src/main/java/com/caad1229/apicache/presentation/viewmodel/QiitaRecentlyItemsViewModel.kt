package com.caad1229.apicache.presentation.viewmodel

import com.caad1229.apicache.data.repository.QiitaRepository
import com.caad1229.apicache.presentation.entity.QiitaItem
import io.reactivex.Single
import javax.inject.Inject

class QiitaRecentlyItemsViewModel @Inject constructor(
        private val repository: QiitaRepository
) : AbsQiitaItemViewModel() {

    override fun useCaseRepository(forceRemote: Boolean): Single<List<QiitaItem>> =
            repository.getItems(forceRemote)
}