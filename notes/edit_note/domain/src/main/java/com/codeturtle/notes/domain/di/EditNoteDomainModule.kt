package com.codeturtle.notes.domain.di

import com.codeturtle.notes.domain.repository.EditNoteRepository
import com.codeturtle.notes.domain.usecase.EditNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object EditNoteDomainModule {
    @Provides
    @Singleton
    fun provideEditNoteUseCase(repository: EditNoteRepository) = EditNoteUseCase(repository)
}