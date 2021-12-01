package com.tvmaze.davidferrandiz.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.davidferrandiz.api.ApiService
import com.davidferrandiz.data.datasources.ShowNetworkDatasource
import com.davidferrandiz.data.datasources.ShowsPagingSource
import com.davidferrandiz.data.repositories.ShowsRepositoryImpl
import com.tvmaze.davidferrandiz.ShowEntityPagingSourceFake
import com.tvmaze.davidferrandiz.fakeEntityPaging
import com.tvmaze.davidferrandiz.fakeShows
import com.tvmaze.davidferrandiz.fakeShowsResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ShowsRepositoryTest {

    private val apiService: ApiService = mockk()
    private val networkDatasource: ShowNetworkDatasource = mockk()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private lateinit var showsRepositoryImpl: ShowsRepositoryImpl

    @Before
    fun setup() {
        showsRepositoryImpl = ShowsRepositoryImpl(networkDatasource, testCoroutineDispatcher)
    }

    @Test
    fun `getShows - PagingData data mapped to entity`() {
        runBlockingTest {
            coEvery {
                networkDatasource.getShows()
            } returns Pager(PagingConfig(pageSize = 2)) {
                ShowsPagingSource(apiService)
            }

            coEvery { apiService.getShows(0) } returns fakeShowsResponse

            val result = showsRepositoryImpl.getShows().first()::class.java
            val expected = fakeEntityPaging.first()::class.java

            Assert.assertEquals(result, expected)
        }
    }

    @Test
    fun `getShows - PagingData when error thrown`() {
        runBlockingTest {
            coEvery {
                networkDatasource.getShows()
            } returns Pager(PagingConfig(pageSize = 2)) {
                ShowsPagingSource(apiService)
            }

            coEvery { apiService.getShows(0) } throws Exception()

            val result = showsRepositoryImpl.getShows().first()::class.java

            val expected = Pager(PagingConfig(pageSize = 2)) {
                ShowEntityPagingSourceFake()
            }.flow.first()::class.java

            Assert.assertEquals(result, expected)
        }
    }

    @Test
    fun `getShowById - getShowById mapped to Show in flow`() {
        runBlockingTest {
            coEvery {
                networkDatasource.getShowById(0)
            } returns fakeShowsResponse[0]

            val result = showsRepositoryImpl.getShowById(0).first()
            val expected = fakeShows[0]

            Assert.assertEquals(result, expected)
        }
    }
}