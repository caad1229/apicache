package com.caad1229.apicache.data.local.realm

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class QiitaItemsRealmEntity(
        @PrimaryKey
        open var userId: String = "",
        open var items: RealmList<QiitaItemRealmEntity> = RealmList(),
        open var createdAt: Date = Date()

) : RealmObject() {
    fun isCached(): Boolean = items.isNotEmpty()

    fun isExpired(): Boolean {
        val currentTime = Date().time
        return ((currentTime - createdAt.time) > EXPIRATION_TIME)
    }

    companion object {
        private const val EXPIRATION_TIME: Long = 60 * 10 * 1000
    }
}