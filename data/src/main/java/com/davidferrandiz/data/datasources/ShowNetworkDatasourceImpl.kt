package com.davidferrandiz.data.datasources

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.davidferrandiz.api.ApiService
import com.davidferrandiz.api.responses.ShowsResponse
import javax.inject.Inject

class ShowNetworkDatasourceImpl @Inject constructor(private val apiService: ApiService) :
    ShowNetworkDatasource {

    override fun getShows(): Pager<Int, ShowsResponse> =
        Pager(PagingConfig(pageSize = 2)) {
            ShowsPagingSource(apiService)
        }

    override suspend fun getShowById(id: Int): ShowsResponse = apiService.getShowById(id)
}