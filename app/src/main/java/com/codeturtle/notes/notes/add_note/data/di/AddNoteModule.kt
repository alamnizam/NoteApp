package com.codeturtle.notes.notes.add_note.data.di

import com.codeturtle.notes.notes.add_note.data.network.AddNoteApiService
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
}