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

    override fun getItems(): Single<List<QiitaItem>> =
            restService.getItems().map { it.map { mapper.mapToEntity(it) } }

    override fun saveItems(items: List<QiitaItem>) {
        throw UnsupportedOperationException("unsupported in remote")
    }

    override fun clearItems() {
        throw UnsupportedOperationException("unsupported in remote")
    }

    override fun getUserItems(userId: String): Single<List<QiitaItem>> =
            restService.getUserItems(userId).map { it.map { mapper.mapToEntity(it) } }

    override fun saveUserItems(userId: String, items: List<QiitaItem>) {
        throw UnsupportedOperationException("unsupported in remote")
    }

    override fun clearUserItems(userId: String) {
        throw UnsupportedOperationException("unsupported in remote")
    }

}