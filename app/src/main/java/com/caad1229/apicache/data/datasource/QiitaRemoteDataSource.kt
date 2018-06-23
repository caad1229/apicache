package com.caad1229.apicache.data.datasource

import com.caad1229.apicache.data.remote.qiita.QiitaRestService
import com.caad1229.apicache.data.remote.qiita.mapper.QiitaItemResponseMapper
import com.caad1229.apicache.presentation.entity.QiitaItem
import io.reactivex.Single
import javax.inject.Inject

class QiitaRemoteDataSource @Inject constructor(
        private val mapper: QiitaItemResponseMapper,
        private val restService: QiitaRestService
) : QiitaDataSource {

    override fun getUserItem(userId: String): Single<List<QiitaItem>> =
            restService.getUserItems(userId).map { it.map { mapper.mapToEntity(it) } }

}