package com.tvmaze.davidferrandiz.datasource

import androidx.paging.PagingSource
import com.davidferrandiz.api.ApiService
import com.davidferrandiz.api.responses.ShowsResponse
import com.davidferrandiz.data.datasources.ShowsPagingSource
import com.tvmaze.davidferrandiz.fakeShowsResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class ShowsDatasourceTest {
    private val apiService: ApiService = mockk()
    private lateinit var pager: ShowsPagingSource

    @Before
    fun setup() {
        pager = ShowsPagingSource(apiService)
    }

    @Test
    fun `getItems - should return page when result is success`() {
        runBlockingTest {
            coEvery {
                apiService.getShows(0)
            } returns fakeShowsResponse

            val expected = PagingSource.LoadResult.Page(
                data = fakeShowsResponse,
                prevKey = null,
                nextKey = 1
            )

            val result = pager.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )

            Assert.assertEquals(expected, result)
        }
    }

    @Test
    fun `getShows - should return error when list is empty`() {
        runBlockingTest {
            coEvery {
                apiService.getShows(0)
            } returns listOf()

            val expected = PagingSource.LoadResult.Error<Int, ShowsResponse>(Exception())::class.java

            val result = pager.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )::class.java

            Assert.assertEquals(expected, result)
        }
    }

    @Test
    fun `getShows - should return error when error es thrown`() {
        runBlockingTest {
            coEvery {
                apiService.getShows(0)
            } throws IOException()

            val expected = PagingSource.LoadResult.Error<Int, ShowsResponse>(IOException())::class.java

            val result = pager.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )::class.java

            Assert.assertEquals(expected, result)
        }
    }
}