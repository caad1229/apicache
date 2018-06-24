package com.caad1229.apicache

import android.app.Application
import com.caad1229.apicache.di.component.AppApplicationComponent
import com.caad1229.apicache.di.component.DaggerAppApplicationComponent
import com.caad1229.apicache.di.module.AppApplicationModule
import com.caad1229.apicache.util.realm.RealmFactory
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.realm.Realm
import okhttp3.OkHttpClient
import javax.inject.Inject

class AppApplication : Application() {

    lateinit var component: AppApplicationComponent
    @Inject
    lateinit var okHttpClient: OkHttpClient
    @Inject
    lateinit var realmFactory: RealmFactory

    private lateinit var realmInMemory: Realm

    override fun onCreate() {
        component = DaggerAppApplicationComponent.builder()
                .appApplicationModule(AppApplicationModule(this))
                .build()
        component.inject(this)

        super.onCreate()

        setupStetho()
        setupRealm()
    }

    override fun onTerminate() {
        realmInMemory.close()
        super.onTerminate()
    }

    private fun setupStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
                                .withLimit(1000)
                                .build())
                        .build())
    }

    private fun setupRealm() {
        // in-memory はすべての　Ｒｅａｌｍ　インスタンスがクローズされた瞬間にデータが破棄されるので
        // Application クラスで保持しておく
        realmInMemory = realmFactory.createRealmInMemoryInstance()
    }
}