package com.codeturtle.notes.notes.edit_note.domain.di

import com.codeturtle.notes.notes.edit_note.domain.repository.EditNoteRepository
import com.codeturtle.notes.notes.edit_note.domain.usecase.EditNoteUseCase
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