package com.caad1229.apicache.presentation.viewmodel

import android.content.Context
import com.caad1229.apicache.presentation.entity.QiitaItem
import com.caad1229.apicache.presentation.entity.QiitaUser
import com.caad1229.apicache.presentation.navigator.QiitaItemNavigator
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class QiitaItemViewModelTest {
    private lateinit var viewModel: QiitaItemViewModel
    private val context: Context = RuntimeEnvironment.application.applicationContext
    private lateinit var item: QiitaItem
    private val createdAt = DateTime(2018, 1, 2, 15, 4)
    private val updatedAt = DateTime(2018, 1, 3, 15, 4)

    private lateinit var mockedNavigator: QiitaItemNavigator

    @Before
    fun setUp() {
        item = QiitaItem(
                id = "id",
                title = "title",
                url = "url",
                likesCount = 100,
                createdAt = createdAt,
                updatedAt = updatedAt,
                user = QiitaUser("name", "user-id", "url")
        )
        mockedNavigator = mock(QiitaItemNavigator::class.java)
        viewModel = QiitaItemViewModel(context, item, mockedNavigator)
    }

    @Test
    fun created() {
        val expected = "が " + DateTimeFormat.forPattern("yyyy/MM/dd").print(createdAt) + " に投稿"
        assertEquals(expected, viewModel.created.get())
    }

    @Test
    fun updateCreated() {
        viewModel.createdAt = updatedAt
        val expected = "が " + DateTimeFormat.forPattern("yyyy/MM/dd").print(updatedAt) + " に投稿"
        assertEquals(expected, viewModel.created.get())
    }

    @Test
    fun onClickUser() {
        viewModel.onClickUser()
        verify(mockedNavigator).navigateToUserDetail(item.user)
    }

    @Test
    fun onClickItem() {
        viewModel.onClickItem()
        verify(mockedNavigator).navigateToItemDetail(item)
    }
}