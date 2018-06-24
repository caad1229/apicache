package com.caad1229.apicache.util.realm

import io.realm.Realm
import io.realm.RealmConfiguration

class RealmFactory (private val inMemoryConfig: RealmConfiguration){

    fun createRealmInstance(): Realm = Realm.getDefaultInstance()

    fun createRealmInMemoryInstance(): Realm = Realm.getInstance(inMemoryConfig)
}