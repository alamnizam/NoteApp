package com.codeturtle.notes.domain.di

import com.codeturtle.notes.domain.repository.NoteListRepository
import com.codeturtle.notes.domain.usecase.NoteListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NoteListDomainModule {
    @Provides
    @Singleton
    fun provideNoteListUseCase(repository: NoteListRepository) = NoteListUseCase(repository)
}