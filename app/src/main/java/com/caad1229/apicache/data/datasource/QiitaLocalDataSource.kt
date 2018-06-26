package com.caad1229.apicache.data.datasource

import android.support.annotation.VisibleForTesting
import com.caad1229.apicache.data.local.mapper.QiitaItemRealmEntityMapper
import com.caad1229.apicache.data.local.realm.QiitaItemRealmEntity
import com.caad1229.apicache.data.local.realm.QiitaItemsRealmEntity
import com.caad1229.apicache.presentation.entity.QiitaItem
import com.caad1229.apicache.util.realm.RealmFactory
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmList
import java.util.*
import javax.inject.Inject

class QiitaLocalDataSource @Inject constructor(
        private val realmMapper: QiitaItemRealmEntityMapper,
        private val realmFactory: RealmFactory
) : QiitaDataSource {

    class NoCacheException : IllegalStateException()

    private fun realm() = realmFactory.createRealmInMemoryInstance()

    override fun getItems(): Single<List<QiitaItem>> =
            getItemsWithKey(RECENTLY_ARTICLE_KEY)

    override fun saveItems(items: List<QiitaItem>) =
            saveItemsWithKey(RECENTLY_ARTICLE_KEY, items)

    override fun clearItems() =
            clearItemsWithKey(RECENTLY_ARTICLE_KEY)

    override fun getUserItems(userId: String): Single<List<QiitaItem>> =
            getItemsWithKey(userId)

    override fun saveUserItems(userId: String, items: List<QiitaItem>) =
            saveItemsWithKey(userId, items)

    override fun clearUserItems(userId: String) = clearItemsWithKey(userId)

    private fun getItemsWithKey(key: String): Single<List<QiitaItem>> {
        realm().use { realm ->
            val result = getQiitaItemsRealmList(realm, key)

            return if (result != null && result.isCached() && !result.isExpired()) {
                Single.just(result.items.map { realmMapper.mapFromEntity(it) })
            } else {
                Single.error(NoCacheException())
            }
        }
    }

    private fun saveItemsWithKey(key: String, items: List<QiitaItem>) {
        val realmList = RealmList<QiitaItemRealmEntity>()
        items.map { item ->
            realmList.add(realmMapper.mapToEntity(item))
        }
        realm().use { realm ->
            realm.executeTransaction {
                realm.insertOrUpdate(QiitaItemsRealmEntity(key, realmList))
            }
        }
    }

    private fun clearItemsWithKey(key: String) {
        val realmList = RealmList<QiitaItemRealmEntity>()
        realm().use { realm ->
            realm.executeTransaction {
                realm.insertOrUpdate(QiitaItemsRealmEntity(key, realmList))
            }
        }
    }

    @VisibleForTesting
    fun getQiitaItemsRealmList(realm: Realm, userId: String): QiitaItemsRealmEntity? =
            realm.where(QiitaItemsRealmEntity::class.java)
                    .equalTo("userId", userId)
                    .findFirst()

    companion object {
        val RECENTLY_ARTICLE_KEY = "article-" + UUID.randomUUID().toString()
    }
}