package com.codeturtle.notes.notes.notes_list.data.di

import com.codeturtle.notes.notes.notes_list.data.network.NoteListApiService
import com.codeturtle.notes.notes.notes_list.data.repository.NoteListRepositoryImpl
import com.codeturtle.notes.notes.notes_list.domain.repository.NoteListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteListDataModule {
    @Provides
    @Singleton
    fun provideNoteListApiService(
        retrofit: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): NoteListApiService {
        return retrofit.client(okHttpClient).build().create(NoteListApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNoteListRepository(apiService: NoteListApiService): NoteListRepository {
        return NoteListRepositoryImpl(apiService)
    }

}