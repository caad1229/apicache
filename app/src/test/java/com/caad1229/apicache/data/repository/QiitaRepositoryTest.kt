package com.caad1229.apicache.data.repository

import com.caad1229.apicache.data.datasource.QiitaLocalDataSource
import com.caad1229.apicache.data.datasource.QiitaRemoteDataSource
import com.caad1229.apicache.presentation.entity.QiitaItem
import io.reactivex.Single
import org.junit.Assert.assertEquals
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
    private lateinit var mockedRemoteUserData: QiitaItem

    private lateinit var mockedLocalDataSource: QiitaLocalDataSource
    private lateinit var mockedLocalUserData: QiitaItem
    private lateinit var mockedLocalData: QiitaItem

    @Before
    fun setUp() {
        mockedRemoteData = mock(QiitaItem::class.java)
        mockedRemoteUserData = mock(QiitaItem::class.java)
        mockedRemoteDataSource = mock(QiitaRemoteDataSource::class.java)
        `when`(mockedRemoteDataSource.getUserItems(userId)).thenReturn(Single.just(listOf(mockedRemoteUserData)))
        `when`(mockedRemoteDataSource.getItems()).thenReturn(Single.just(listOf(mockedRemoteData)))

        mockedLocalUserData = mock(QiitaItem::class.java)
        mockedLocalData = mock(QiitaItem::class.java)
        mockedLocalDataSource = mock(QiitaLocalDataSource::class.java)
        `when`(mockedLocalDataSource.getUserItems(userId)).thenReturn(Single.just(listOf(mockedLocalUserData)))
        `when`(mockedLocalDataSource.getItems()).thenReturn(Single.just(listOf(mockedLocalData)))

        repository = QiitaRepository(mockedLocalDataSource, mockedRemoteDataSource)
    }

    @Test
    fun getUserItem_キャッシュあり() {
        val result = repository.getUserItems(userId).blockingGet()
        assertEquals(mockedLocalUserData, result.first())
    }

    @Test
    fun getUserItem_キャッシュなし() {
        `when`(mockedLocalDataSource.getUserItems(userId))
                .thenReturn(Single.error(QiitaLocalDataSource.NoCacheException()))

        val result = repository.getUserItems(userId).blockingGet()
        assertEquals(mockedRemoteUserData, result.first())
    }

    @Test
    fun getUserItem_リモートから強制取得() {
        val result = repository.getUserItems(userId, true).blockingGet()
        assertEquals(mockedRemoteUserData, result.first())
    }

    @Test
    fun getItem_キャッシュあり() {
        val result = repository.getItems().blockingGet()
        assertEquals(mockedLocalData, result.first())
    }

    @Test
    fun getItem_キャッシュなし() {
        `when`(mockedLocalDataSource.getItems())
                .thenReturn(Single.error(QiitaLocalDataSource.NoCacheException()))

        val result = repository.getItems().blockingGet()
        assertEquals(mockedRemoteData, result.first())
    }

    @Test
    fun getItem_リモートから強制取得() {
        val result = repository.getItems(true).blockingGet()
        assertEquals(mockedRemoteData, result.first())
    }
}