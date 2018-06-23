package com.caad1229.apicache.data.repository

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

    @Before
    fun setUp() {
        mockedRemoteData = mock(QiitaItem::class.java)
        mockedRemoteDataSource = mock(QiitaRemoteDataSource::class.java)
        `when`(mockedRemoteDataSource.getUserItem(userId)).thenReturn(Single.just(listOf(mockedRemoteData)))

        repository = QiitaRepository(mockedRemoteDataSource)
    }

    @Test
    fun getUserItem_リモートから取得() {
        val result = repository.getUserItems(userId).blockingGet()
        assertEquals(mockedRemoteData, result.first())
    }
}