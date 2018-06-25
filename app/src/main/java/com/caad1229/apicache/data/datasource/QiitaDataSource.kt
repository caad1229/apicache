package com.caad1229.apicache.data.datasource

import com.caad1229.apicache.presentation.entity.QiitaItem
import io.reactivex.Single
import java.util.*

interface QiitaDataSource {
    fun getItems(key: String = ITEM_KEY): Single<List<QiitaItem>>

    fun getUserItems(userId: String): Single<List<QiitaItem>>

    fun saveItems(key: String, items: List<QiitaItem>)

    fun clearUserItems(userId: String)

    companion object {
        val ITEM_KEY = "article-" + UUID.randomUUID().toString()
    }
}