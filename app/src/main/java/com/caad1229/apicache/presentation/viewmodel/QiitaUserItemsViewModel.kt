package com.caad1229.apicache.presentation.viewmodel

import com.caad1229.apicache.data.repository.QiitaRepository
import com.caad1229.apicache.presentation.entity.QiitaItem
import io.reactivex.Single
import javax.inject.Inject

class QiitaUserItemsViewModel @Inject constructor(
        private val repository: QiitaRepository
) : AbsQiitaItemViewModel() {

    var userName = "caad1229"

    override fun useCaseRepository(forceRemote: Boolean): Single<List<QiitaItem>> =
            repository.getUserItems(userName, forceRemote)
}