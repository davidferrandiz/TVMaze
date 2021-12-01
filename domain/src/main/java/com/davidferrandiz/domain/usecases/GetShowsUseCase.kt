package com.davidferrandiz.domain.usecases

import androidx.paging.PagingData
import com.davidferrandiz.domain.entities.Show
import com.davidferrandiz.domain.repositoryinterfaces.ShowsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShowsUseCase @Inject constructor(private val showsRepository: ShowsRepository) {

    suspend operator fun invoke(): Flow<PagingData<Show>> =
        showsRepository.getShows()

}