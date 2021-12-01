package com.tvmaze.davidferrandiz

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingState
import com.davidferrandiz.api.responses.ShowImageResponse
import com.davidferrandiz.api.responses.ShowRatingResponse
import com.davidferrandiz.api.responses.ShowsResponse
import com.davidferrandiz.domain.entities.Show
import com.davidferrandiz.domain.entities.ShowImage
import com.davidferrandiz.presentation.uidata.ShowDetailUI
import com.davidferrandiz.presentation.uidata.ShowListItem
import kotlinx.coroutines.flow.first

val fakeImageResponse = ShowImageResponse("a.jpg", "b.jpg")
val fakeRatingResponse = ShowRatingResponse(5.0)

val fakeShowsResponse = listOf(
    ShowsResponse(1,
        "a",
        fakeRatingResponse,
        "summary",
        fakeImageResponse,
        "www.a.com",
        "2021-01-01"),
    ShowsResponse(2,
        "a",
        fakeRatingResponse,
        "summary",
        fakeImageResponse,
        "www.a.com",
        "2021-01-01"),
    ShowsResponse(3,
        "a",
        fakeRatingResponse,
        "summary",
        fakeImageResponse,
        "www.a.com",
        "2021-01-01"),
    ShowsResponse(4,
        "a",
        fakeRatingResponse,
        "summary",
        fakeImageResponse,
        "www.a.com",
        "2021-01-01")
)

val fakeImage = ShowImage("a.jpg", "b.jpg")

val fakeShows = listOf(
    Show(1, "a", "summary", 5.0, fakeImage, "www.a.com", "2021-01-01"),
    Show(2, "a", "summary", 5.0, fakeImage, "www.a.com", "2021-01-01"),
    Show(3, "a", "summary", 5.0, fakeImage, "www.a.com", "2021-01-01"),
    Show(4, "a", "summary", 5.0, fakeImage, "www.a.com", "2021-01-01")
)

val fakeUIShows = listOf(
    ShowListItem(1, "a", fakeImage.medium),
    ShowListItem(2, "a", fakeImage.medium),
    ShowListItem(3, "a", fakeImage.medium),
    ShowListItem(4, "a", fakeImage.medium)
)

val fakeDetailShow =
    ShowDetailUI("a", fakeImage.original, 5.0, "summary", "www.a.com", "2021-01-01")

val fakeEntityPaging = Pager(PagingConfig(pageSize = 2)) {
    ShowEntityPagingSourceFake()
}.flow

val fakeUIPaging = Pager(PagingConfig(pageSize = 2)) {
    ShowUIPagingSourceFake()
}.flow

class ShowEntityPagingSourceFake :
    PagingSource<Int, Show>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Show> {
        return try {
            LoadResult.Page(
                data = fakeShows,
                prevKey = null,
                nextKey = 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Show>): Int {
        return 0
    }
}

class ShowUIPagingSourceFake :
    PagingSource<Int, ShowListItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ShowListItem> {
        return try {
            LoadResult.Page(
                data = fakeUIShows,
                prevKey = null,
                nextKey = 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ShowListItem>): Int {
        return 0
    }
}