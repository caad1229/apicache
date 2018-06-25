package com.caad1229.apicache.di.module

import android.app.Application
import com.caad1229.apicache.util.realm.RealmFactory
import dagger.Module
import org.mockito.Mockito.mock

@Module
class TestAppApplicationModule(application: Application) : AppApplicationModule(application) {

    override fun provideRealmFactory(): RealmFactory =
            mock(RealmFactory::class.java)
}