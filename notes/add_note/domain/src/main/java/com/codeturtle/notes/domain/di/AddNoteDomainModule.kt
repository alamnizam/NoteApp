package com.codeturtle.notes.domain.di

import com.codeturtle.notes.domain.repository.AddNoteRepository
import com.codeturtle.notes.domain.usecase.AddNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AddNoteDomainModule {
    @Provides
    @Singleton
    fun provideAddNoteUseCase(repository: AddNoteRepository) = AddNoteUseCase(repository)
}