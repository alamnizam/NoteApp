package com.codeturtle.notes.domain.di

import com.codeturtle.notes.domain.repository.RegisterRepository
import com.codeturtle.notes.domain.usecase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RegisterDomainModule {
    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: RegisterRepository) = RegisterUseCase(repository)
}