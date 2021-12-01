package com.davidferrandiz.di

import com.davidferrandiz.domain.repositoryinterfaces.ShowsRepository
import com.davidferrandiz.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideShowsUseCase(showsRepository: ShowsRepository): GetShowsUseCase =
        GetShowsUseCase(showsRepository)

    @Provides
    fun provideShowByIdUseCase(showsRepository: ShowsRepository): GetShowByIdUseCase =
        GetShowByIdUseCase(showsRepository)

}