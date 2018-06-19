package com.caad1229.apicache.data.remote.qiita

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
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
    fun ttest() {
        val response = createMockResponse().setBody(loadFromJsonResoponse())
        server.enqueue(response)
        val service = createService(server.url("/"))

        val result = service.getUserItems("caad1229").blockingGet()
    }

    private fun createMockResponse() = MockResponse()
            .addHeader("Content-Type", "application/json")
            .setResponseCode(200)

    private fun createService(url: HttpUrl) = Retrofit.Builder()
            .baseUrl(url.toString())
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QiitaRestService::class.java)

    private fun createClient() = OkHttpClient.Builder().build()

    private fun loadFromJsonResoponse(): String {
        val reader = FileReader("./app/src/test/data-fixtures/qiita-items.json")
        val body = reader.readText()
        reader.close()
        return body
    }
}