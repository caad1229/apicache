package com.caad1229.apicache.data.repository

import com.caad1229.apicache.data.datasource.QiitaLocalDataSource
import com.caad1229.apicache.data.datasource.QiitaRemoteDataSource
import com.caad1229.apicache.presentation.entity.QiitaItem
import io.reactivex.Single
import javax.inject.Inject

class QiitaRepository @Inject constructor(
        private val localDataSource: QiitaLocalDataSource,
        private val remoteDataSource: QiitaRemoteDataSource
) {
    fun getUserItems(userId: String, forceRemote: Boolean = false): Single<List<QiitaItem>> {
        val remote: Single<List<QiitaItem>> =
                remoteDataSource.getUserItems(userId).doOnSuccess { localDataSource.saveUserItems(userId, it) }
        val local: Single<List<QiitaItem>> =
                localDataSource.getUserItems(userId).onErrorResumeNext { remote }

        return if (forceRemote) {
            remote
        } else {
            local
        }
    }
}