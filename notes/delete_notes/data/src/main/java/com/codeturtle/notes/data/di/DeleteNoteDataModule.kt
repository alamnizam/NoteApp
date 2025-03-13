package com.codeturtle.notes.data.di

import com.codeturtle.notes.data.network.DeleteNoteApiService
import com.codeturtle.notes.data.repository.DeleteNoteRepositoryImpl
import com.codeturtle.notes.domain.repository.DeleteNoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DeleteNoteDataModule {
    @Provides
    @Singleton
    fun provideDeleteNoteApiService(
        retrofit: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): DeleteNoteApiService {
        return retrofit.client(okHttpClient).build().create(DeleteNoteApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDeleteNoteRepository(apiService: DeleteNoteApiService): DeleteNoteRepository {
        return DeleteNoteRepositoryImpl(apiService)
    }}