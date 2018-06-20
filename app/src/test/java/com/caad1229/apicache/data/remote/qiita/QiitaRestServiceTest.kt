package com.caad1229.apicache.data.remote.qiita

import com.caad1229.apicache.api.gson.CustomGson
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.joda.time.DateTime
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.FileReader

@RunWith(RobolectricTestRunner::class)
class QiitaRestServiceTest {

    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun getUserItems() {
        val response = createMockResponse().setBody(loadFromJsonResponse())
        server.enqueue(response)
        val service = createService(server.url("/"))

        val result = service.getUserItems("caad1229").blockingGet()
        assertEquals(1, result.size)
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

    private fun createMockResponse() = MockResponse()
            .addHeader("Content-Type", "application/json")
            .setResponseCode(200)

    private fun createService(url: HttpUrl) = Retrofit.Builder()
            .baseUrl(url.toString())
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create(CustomGson.gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(QiitaRestService::class.java)

    private fun createClient() = OkHttpClient.Builder().build()

    private fun loadFromJsonResponse(): String {
        val reader = FileReader("./src/test/data-fixtures/qiita-items.json")
        val body = reader.readText()
        reader.close()
        return body
    }
}