package com.codeturtle.notes.data.di

import com.codeturtle.notes.data.network.RegisterApiService
import com.codeturtle.notes.data.repository.RegisterRepositoryImpl
import com.codeturtle.notes.domain.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object RegisterDataModule {
    private val loggingInterceptor = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    fun provideRegisterApiService(retrofit: Retrofit.Builder): RegisterApiService {
        return retrofit.client(loggingInterceptor).build().create(RegisterApiService::class.java)
    }

    @Provides
    fun provideRegisterRepository(apiService: RegisterApiService): RegisterRepository {
        return RegisterRepositoryImpl(apiService)
    }
}