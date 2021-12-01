package com.davidferrandiz.domain.repositoryinterfaces

import androidx.paging.PagingData
import com.davidferrandiz.domain.entities.Show
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {
    suspend fun getShows() : Flow<PagingData<Show>>
    suspend fun getShowById(id: Int) : Flow<Show>
}