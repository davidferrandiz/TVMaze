package com.davidferrandiz.data.datasources

import androidx.paging.Pager
import com.davidferrandiz.api.responses.ShowsResponse

interface ShowNetworkDatasource {
    fun getShows(): Pager<Int, ShowsResponse>
    suspend fun getShowById(id: Int): ShowsResponse
}