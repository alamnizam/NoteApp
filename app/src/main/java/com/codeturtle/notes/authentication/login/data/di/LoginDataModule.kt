package com.codeturtle.notes.authentication.login.data.di

import com.codeturtle.notes.authentication.login.data.network.LoginApiService
import com.codeturtle.notes.authentication.login.data.repository.LoginRepositoryImpl
import com.codeturtle.notes.authentication.login.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object LoginDataModule {
    @Provides
    fun provideLoginApiService(retrofit: Retrofit): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
    }

    @Provides
    fun provideLoginRepository(apiService: LoginApiService): LoginRepository {
        return LoginRepositoryImpl(apiService)
    }
}