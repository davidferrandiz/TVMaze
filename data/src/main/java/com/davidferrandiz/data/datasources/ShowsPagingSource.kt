package com.davidferrandiz.data.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.davidferrandiz.api.ApiService
import com.davidferrandiz.api.responses.ShowsResponse

class ShowsPagingSource(private val apiService: ApiService) :
    PagingSource<Int, ShowsResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ShowsResponse> {
        try {
            val currentLoadingPageKey = params.key ?: 0
            val response = apiService.getShows(currentLoadingPageKey)
            val responseData = mutableListOf<ShowsResponse>()

            if(response.isEmpty()) {
                return LoadResult.Error(Exception())
            }

            responseData.addAll(response)

            val prevKey = if (currentLoadingPageKey == 0) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ShowsResponse>): Int? {
        return 0
    }
}