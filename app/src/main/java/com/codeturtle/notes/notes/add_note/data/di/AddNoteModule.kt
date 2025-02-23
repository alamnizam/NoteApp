package com.codeturtle.notes.notes.add_note.data.di

import com.codeturtle.notes.notes.add_note.data.network.AddNoteApiService
import com.codeturtle.notes.notes.add_note.data.repository.AddNoteRepositoryImpl
import com.codeturtle.notes.notes.add_note.domain.repository.AddNoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AddNoteModule {
    @Provides
    @Singleton
    fun provideAddNoteApiService(
        retrofit: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): AddNoteApiService {
        return retrofit.client(okHttpClient).build().create(AddNoteApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAddNoteRepository(apiService: AddNoteApiService): AddNoteRepository {
        return AddNoteRepositoryImpl(apiService)
    }
}