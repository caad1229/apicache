package com.caad1229.apicache.di.module

import android.app.Application
import com.caad1229.apicache.BuildConfig
import com.caad1229.apicache.api.gson.CustomGson
import com.caad1229.apicache.data.datasource.QiitaLocalDataSource
import com.caad1229.apicache.data.datasource.QiitaRemoteDataSource
import com.caad1229.apicache.data.local.mapper.QiitaItemRealmEntityMapper
import com.caad1229.apicache.data.local.mapper.QiitaUserRealmEntityMapper
import com.caad1229.apicache.data.remote.qiita.QiitaRestService
import com.caad1229.apicache.data.remote.qiita.mapper.QiitaItemResponseMapper
import com.caad1229.apicache.data.repository.QiitaRepository
import com.caad1229.apicache.di.qualifier.ForQiita
import com.caad1229.apicache.util.realm.RealmFactory
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
open class AppApplicationModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application = application

    @Singleton
    @Provides
    open fun provideRealmFactory(): RealmFactory {
        Realm.init(application)
        val inMemoryConfig = RealmConfiguration.Builder()
                .name("in-memory.realm")
                .inMemory()
                .build()
        return RealmFactory(inMemoryConfig)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = createOkHttpClientBuilder().build()

    @Singleton
    @Provides
    fun provideQiitaRestService(@ForQiita retrofit: Retrofit): QiitaRestService =
            retrofit.create(QiitaRestService::class.java)

    @Singleton
    @Provides
    @ForQiita
    fun provideQiitaRetrofit(okHttpClient: OkHttpClient): Retrofit =
            createRetrofit("https://qiita.com/api/v2/", okHttpClient)

    @Singleton
    @Provides
    fun provideQiitaRemoteDataSource(restService: QiitaRestService): QiitaRemoteDataSource =
            QiitaRemoteDataSource(QiitaItemResponseMapper(), restService)

    @Singleton
    @Provides
    fun provideQiitaLocalDataSource(realmFactory: RealmFactory): QiitaLocalDataSource =
            QiitaLocalDataSource(QiitaItemRealmEntityMapper(QiitaUserRealmEntityMapper()), realmFactory)

    @Singleton
    @Provides
    fun provideQiitaRepository(localDataSource: QiitaLocalDataSource, remoteDataSource: QiitaRemoteDataSource): QiitaRepository {
        return QiitaRepository(localDataSource, remoteDataSource)
    }

    private fun createOkHttpClientBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(StethoInterceptor())
        }
        return builder
    }

    private fun createRetrofit(endpoint: String, client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl(endpoint)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(CustomGson.gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

    companion object {
        private const val CONNECT_TIMEOUT_MILLIS: Long = 15 * 1000
        private const val READ_TIMEOUT_MILLIS: Long = 60 * 1000
    }
}