package com.davidferrandiz.di

import com.davidferrandiz.api.ApiService
import com.davidferrandiz.data.datasources.ShowNetworkDatasource
import com.davidferrandiz.data.repositories.ShowsRepositoryImpl
import com.davidferrandiz.domain.repositoryinterfaces.ShowsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideShowsRepository(
        networkDatasource: ShowNetworkDatasource,
        @Named("IO") dispatcher: CoroutineDispatcher,
    ): ShowsRepository =
        ShowsRepositoryImpl(networkDatasource, dispatcher)

}