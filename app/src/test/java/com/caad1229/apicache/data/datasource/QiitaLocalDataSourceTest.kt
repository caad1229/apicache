package com.caad1229.apicache.data.datasource

import com.caad1229.apicache.api.gson.CustomGson
import com.caad1229.apicache.data.local.mapper.QiitaItemRealmEntityMapper
import com.caad1229.apicache.data.local.mapper.QiitaUserRealmEntityMapper
import com.caad1229.apicache.data.local.realm.QiitaItemRealmEntity
import com.caad1229.apicache.data.local.realm.QiitaUserItemsRealmEntity
import com.caad1229.apicache.data.remote.qiita.mapper.QiitaItemResponseMapper
import com.caad1229.apicache.data.remote.qiita.response.ItemResponse
import com.caad1229.apicache.util.realm.RealmFactory
import com.google.gson.reflect.TypeToken
import io.realm.Realm
import io.realm.RealmList
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import java.io.FileReader

@RunWith(RobolectricTestRunner::class)
class QiitaLocalDataSourceTest {

    private lateinit var dataSource: QiitaLocalDataSource
    private lateinit var responseMapper: QiitaItemResponseMapper
    private lateinit var realmMapper: QiitaItemRealmEntityMapper
    private val userId = "caad1229"

    private lateinit var mockedRealmFactory: RealmFactory
    private lateinit var mockedRealm: Realm

    @Before
    fun setUp() {
        mockedRealmFactory = mock(RealmFactory::class.java)
        mockedRealm = mock(Realm::class.java)
        `when`(mockedRealmFactory.createRealmInMemoryInstance()).thenReturn(mockedRealm)

        realmMapper = QiitaItemRealmEntityMapper(QiitaUserRealmEntityMapper())
        responseMapper = QiitaItemResponseMapper()
        dataSource = spy(QiitaLocalDataSource(realmMapper, mockedRealmFactory))
    }

    @Test
    fun getUserItems_キャッシュあり() {
        val realmList = RealmList<QiitaItemRealmEntity>()
        qiitaItemRealmEntityList().map { realmList.add(it) }

        val cacheData = QiitaUserItemsRealmEntity(userId, realmList)
        doReturn(cacheData).`when`(dataSource).getQiitaItemsRealmList(mockedRealm, userId)

        val result = dataSource.getUserItems(userId).blockingGet()
        result.run {
            assertEquals(qiitaItemRealmEntityList().size, size)
            assertEquals(cacheData.items.map { realmMapper.mapFromEntity(it) }, this)
        }
    }

    @Test
    fun getUserItems_キャッシュなし() {
        // キャッシュなし
        doReturn(null).`when`(dataSource).getQiitaItemsRealmList(mockedRealm, userId)

        var error: Throwable? = null
        dataSource.getUserItems(userId)
                .subscribeOn(Schedulers.trampoline())
                .subscribe({ fail() }, { error = it })
        assertTrue(error is QiitaLocalDataSource.NoCacheException)
    }

    private fun qiitaItemRealmEntityList(): List<QiitaItemRealmEntity> {
        val reader = FileReader("./src/test/data-fixtures/qiita-items.json")
        val type = object : TypeToken<List<ItemResponse>>() {}.type
        return CustomGson.gson.fromJson<List<ItemResponse>>(reader, type)
                .map { responseMapper.mapToEntity(it) }
                .map { realmMapper.mapToEntity(it) }
    }
}