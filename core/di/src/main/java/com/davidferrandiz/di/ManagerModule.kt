package com.davidferrandiz.di

import android.content.Context
import com.davidferrandiz.presentation.uidata.utils.FeedbackCreator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ManagerModule {

    @Provides
    @Singleton
    fun feedbackCreatorProvider(@ApplicationContext context: Context): FeedbackCreator =
        FeedbackCreator(context)
}