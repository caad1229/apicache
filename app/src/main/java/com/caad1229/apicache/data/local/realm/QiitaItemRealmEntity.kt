package com.caad1229.apicache.data.local.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class QiitaItemRealmEntity(
        @PrimaryKey
        open var id: String = "",
        open var title: String = "",
        open var url: String = "",
        open var likesCount: Int = 0,
        open var createdAt: Date = Date(0),
        open var updatedAt: Date = Date(0),
        open var user: QiitaUserRealmEntity = QiitaUserRealmEntity()
) : RealmObject()
