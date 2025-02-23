package com.codeturtle.notes.notes.notes_list.domain.di

import com.codeturtle.notes.notes.notes_list.domain.repository.NoteListRepository
import com.codeturtle.notes.notes.notes_list.domain.usecase.NoteListUseCase
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