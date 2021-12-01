package com.davidferrandiz.domain.usecases

import com.davidferrandiz.domain.entities.Show
import com.davidferrandiz.domain.repositoryinterfaces.ShowsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetShowByIdUseCase @Inject constructor(private val showsRepository: ShowsRepository) {

    suspend operator fun invoke(id: Int): Flow<Show> =
        showsRepository.getShowById(id)

}