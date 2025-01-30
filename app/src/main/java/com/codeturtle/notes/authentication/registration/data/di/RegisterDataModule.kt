package com.codeturtle.notes.authentication.registration.data.di

import com.codeturtle.notes.authentication.registration.data.network.RegisterApiService
import com.codeturtle.notes.authentication.registration.data.repository.RegisterRepositoryImpl
import com.codeturtle.notes.authentication.registration.domain.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object RegisterDataModule {
    @Provides
    fun provideRegisterApiService(retrofit: Retrofit): RegisterApiService {
        return retrofit.create(RegisterApiService::class.java)
    }

    @Provides
    fun provideRegisterRepository(registerApiService: RegisterApiService): RegisterRepository {
        return RegisterRepositoryImpl(registerApiService)
    }
}