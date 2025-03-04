package com.codeturtle.notes.notes.edit_note.data.di

import com.codeturtle.notes.notes.edit_note.data.network.EditNoteApiService
import com.codeturtle.notes.notes.edit_note.data.repository.EditNoteRepositoryImpl
import com.codeturtle.notes.notes.edit_note.domain.repository.EditNoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EditNoteDataModule {
    @Provides
    @Singleton
    fun provideEditNoteApiService(
        retrofit: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): EditNoteApiService {
        return retrofit.client(okHttpClient).build().create(EditNoteApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideEditNoteRepository(apiService: EditNoteApiService): EditNoteRepository {
        return EditNoteRepositoryImpl(apiService)
    }
}