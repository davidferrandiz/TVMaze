package com.davidferrandiz.data.repositories

import androidx.paging.PagingData
import androidx.paging.map
import com.davidferrandiz.api.responses.ShowsResponse
import com.davidferrandiz.data.datasources.ShowNetworkDatasource
import com.davidferrandiz.data.toDomain
import com.davidferrandiz.domain.entities.Show
import com.davidferrandiz.domain.repositoryinterfaces.ShowsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShowsRepositoryImpl @Inject constructor(
    private val networkDatasource: ShowNetworkDatasource,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ShowsRepository {

    override suspend fun getShows(): Flow<PagingData<Show>> =
        networkDatasource.getShows()
            .flow.map { response ->
                response.map {
                    it.toDomain()
                }
            }.flowOn(coroutineDispatcher)

    override suspend fun getShowById(id: Int): Flow<Show> =
        flow {
            emit(networkDatasource.getShowById(id))
        }.map { value: ShowsResponse ->
            value.toDomain()
        }.flowOn(coroutineDispatcher)
}


