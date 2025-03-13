package com.codeturtle.notes.domain.di

import com.codeturtle.notes.domain.repository.DeleteNoteRepository
import com.codeturtle.notes.domain.usecase.DeleteNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DeleteNoteDomainModule {
    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(repository: DeleteNoteRepository) = DeleteNoteUseCase(repository)
}