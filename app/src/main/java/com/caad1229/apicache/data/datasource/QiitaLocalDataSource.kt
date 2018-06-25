package com.caad1229.apicache.data.datasource

import android.support.annotation.VisibleForTesting
import com.caad1229.apicache.data.local.mapper.QiitaItemRealmEntityMapper
import com.caad1229.apicache.data.local.realm.QiitaItemRealmEntity
import com.caad1229.apicache.data.local.realm.QiitaUserItemsRealmEntity
import com.caad1229.apicache.presentation.entity.QiitaItem
import com.caad1229.apicache.util.realm.RealmFactory
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmList
import javax.inject.Inject

class QiitaLocalDataSource @Inject constructor(
        private val realmMapper: QiitaItemRealmEntityMapper,
        private val realmFactory: RealmFactory
) : QiitaDataSource {

    class NoCacheException : IllegalStateException()

    private fun realm() = realmFactory.createRealmInMemoryInstance()

    override fun getItems(key: String): Single<List<QiitaItem>> {
        realm().use { realm ->
            val result = getQiitaItemsRealmList(realm, key)

            return if (result != null && result.isCached() && !result.isExpired()) {
                Single.just(result.items.map { realmMapper.mapFromEntity(it) })
            } else {
                Single.error(NoCacheException())
            }
        }
    }

    override fun getUserItems(userId: String): Single<List<QiitaItem>> = getItems(userId)

    override fun saveItems(key: String, items: List<QiitaItem>) {
        val realmList = RealmList<QiitaItemRealmEntity>()
        items.map { item ->
            realmList.add(realmMapper.mapToEntity(item))
        }
        realm().use { realm ->
            realm.executeTransaction {
                realm.insertOrUpdate(QiitaUserItemsRealmEntity(key, realmList))
            }
        }
    }

    override fun clearUserItems(userId: String) {
        val realmList = RealmList<QiitaItemRealmEntity>()
        realm().use { realm ->
            realm.executeTransaction {
                realm.insertOrUpdate(QiitaUserItemsRealmEntity(userId, realmList))
            }
        }
    }

    @VisibleForTesting
    fun getQiitaItemsRealmList(realm: Realm, userId: String): QiitaUserItemsRealmEntity? =
            realm.where(QiitaUserItemsRealmEntity::class.java)
                    .equalTo("userId", userId)
                    .findFirst()
}