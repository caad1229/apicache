package com.caad1229.apicache.data.local.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class QiitaUserRealmEntity(
        open var name: String = "",
        @PrimaryKey
        open var id: String = "",
        open var profileImageUrl: String = ""
) : RealmObject()
