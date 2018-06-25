package com.caad1229.apicache.data.repository

import com.caad1229.apicache.data.datasource.QiitaRemoteDataSource
import com.caad1229.apicache.presentation.entity.QiitaItem
import io.reactivex.Single
import javax.inject.Inject

class QiitaRepository @Inject constructor(
        private val remoteDataSource: QiitaRemoteDataSource
) {
    fun getUserItems(userId: String): Single<List<QiitaItem>> {
        return remoteDataSource.getUserItems(userId)
    }
}