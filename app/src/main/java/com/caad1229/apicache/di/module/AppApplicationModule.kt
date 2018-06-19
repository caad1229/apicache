package com.caad1229.apicache.di.module

import android.app.Application
import com.caad1229.apicache.data.remote.qiita.QiitaRestService
import com.caad1229.apicache.di.qualifier.ForQiita
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppApplicationModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application = application

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                    .readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                    .build()

    @Singleton
    @Provides
    fun provideQiitaRestService(@ForQiita retrofit: Retrofit): QiitaRestService =
            retrofit.create(QiitaRestService::class.java)

    @Singleton
    @Provides
    @ForQiita
    fun provideQiitaRetrofit(okHttpClient: OkHttpClient): Retrofit =
            createRetrofit("https://qiita.com/", okHttpClient)


    private fun createRetrofit(endpoint: String, client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl(endpoint)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

    companion object {
        private const val CONNECT_TIMEOUT_MILLIS: Long = 15 * 1000
        private const val READ_TIMEOUT_MILLIS: Long = 60 * 1000
    }
}