package com.caad1229.apicache.data.datasource

import com.caad1229.apicache.presentation.entity.QiitaItem
import io.reactivex.Single

interface QiitaDataSource {
    fun getUserItem(userId: String): Single<List<QiitaItem>>
}