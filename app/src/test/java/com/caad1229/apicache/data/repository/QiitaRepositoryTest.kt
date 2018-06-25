package com.caad1229.apicache.data.repository

import com.caad1229.apicache.data.datasource.QiitaLocalDataSource
import com.caad1229.apicache.data.datasource.QiitaRemoteDataSource
import com.caad1229.apicache.presentation.entity.QiitaItem
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class QiitaRepositoryTest {

    private lateinit var repository: QiitaRepository
    private val userId: String = "user12345"

    private lateinit var mockedRemoteDataSource: QiitaRemoteDataSource
    private lateinit var mockedRemoteData: QiitaItem

    private lateinit var mockedLocalDataSource: QiitaLocalDataSource
    private lateinit var mockedLocalData: QiitaItem

    @Before
    fun setUp() {
        mockedRemoteData = mock(QiitaItem::class.java)
        mockedRemoteDataSource = mock(QiitaRemoteDataSource::class.java)
        `when`(mockedRemoteDataSource.getUserItems(userId)).thenReturn(Single.just(listOf(mockedRemoteData)))

        mockedLocalData = mock(QiitaItem::class.java)
        mockedLocalDataSource = mock(QiitaLocalDataSource::class.java)

        repository = QiitaRepository(mockedLocalDataSource, mockedRemoteDataSource)
    }

    @Test
    fun getUserItem_キャッシュあり() {
        `when`(mockedLocalDataSource.getUserItems(userId)).thenReturn(Single.just(listOf(mockedLocalData)))

        val result = repository.getUserItems(userId).blockingGet()
        assertEquals(mockedLocalData, result.first())
    }

    @Test
    fun getUserItem_キャッシュなし() {
        `when`(mockedLocalDataSource.getUserItems(userId))
                .thenReturn(Single.error(QiitaLocalDataSource.NoCacheException()))

        val result = repository.getUserItems(userId).blockingGet()
        assertEquals(mockedRemoteData, result.first())
    }

    @Test
    fun getUserItem_リモートから強制取得() {
        `when`(mockedLocalDataSource.getUserItems(userId)).thenReturn(Single.just(listOf(mockedLocalData)))

        val result = repository.getUserItems(userId, true).blockingGet()
        assertEquals(mockedRemoteData, result.first())
    }
}