package com.caad1229.apicache.data.datasource

import com.caad1229.apicache.api.gson.CustomGson
import com.caad1229.apicache.data.remote.qiita.QiitaRestService
import com.caad1229.apicache.data.remote.qiita.mapper.QiitaItemResponseMapper
import com.caad1229.apicache.data.remote.qiita.response.ItemResponse
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import java.io.FileReader

@RunWith(RobolectricTestRunner::class)
class QiitaRemoteDataSourceTest {

    private lateinit var dataSource: QiitaRemoteDataSource
    private val userId = "user111"

    private lateinit var mockedRestService: QiitaRestService

    @Before
    fun setUp() {
        mockedRestService = mock(QiitaRestService::class.java)
        dataSource = QiitaRemoteDataSource(QiitaItemResponseMapper(), mockedRestService)
    }

    @Test
    fun getUserItem() {
        `when`(mockedRestService.getUserItems(userId)).thenReturn(Single.just(loadFromJsonResponse()))

        val result = dataSource.getUserItem(userId).blockingGet()
        result[0].run {
            assertEquals("4bd431809afb1bb99e4f", id)
            assertEquals("Example title", title)
            assertEquals("https://qiita.com/yaotti/items/4bd431809afb1bb99e4f", url)
            assertEquals(100, likesCount)
            assertEquals(DateTime.parse("2000-01-01T00:00:00+00:00"), createdAt)
            assertEquals(DateTime.parse("2000-01-01T00:00:00+00:00"), updatedAt)

            // user
            assertEquals("yaotti", user.id)
            assertEquals("Hiroshige Umino", user.name)
            assertEquals("https://si0.twimg.com/profile_images/2309761038/1ijg13pfs0dg84sk2y0h_normal.jpeg", user.profileImageUrl)
        }
    }

    private fun loadFromJsonResponse(): List<ItemResponse> {
        val reader = FileReader("./src/test/data-fixtures/qiita-items.json")
        val type = object : TypeToken<List<ItemResponse>>() {}.type
        return CustomGson.gson.fromJson<List<ItemResponse>>(reader, type)
    }
}