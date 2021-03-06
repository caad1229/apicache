package com.caad1229.apicache.data.datasource

import com.caad1229.apicache.presentation.entity.QiitaItem
import io.reactivex.Single

interface QiitaDataSource {
    fun getItems(): Single<List<QiitaItem>>

    fun saveItems(items: List<QiitaItem>)

    fun clearItems()

    fun getUserItems(userId: String): Single<List<QiitaItem>>

    fun saveUserItems(userId: String, items: List<QiitaItem>)

    fun clearUserItems(userId: String)
}