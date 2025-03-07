package com.codeturtle.notes.data.di

import com.codeturtle.notes.data.repository.LoginRepositoryImpl
import com.codeturtle.notes.domain.repository.LoginRepository
import com.codeturtle.notes.data.network.LoginApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object LoginDataModule {
    private val loggingInterceptor = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    fun provideLoginApiService(retrofit: Retrofit.Builder): LoginApiService {
        return retrofit.client(loggingInterceptor).build().create(LoginApiService::class.java)
    }

    @Provides
    fun provideLoginRepository(apiService: LoginApiService): LoginRepository {
        return LoginRepositoryImpl(apiService)
    }
}