package com.codeturtle.notes.authentication.registration.domain.di

import com.codeturtle.notes.authentication.registration.domain.repository.RegisterRepository
import com.codeturtle.notes.authentication.registration.domain.use_case.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RegisterModule {
    @Provides
    fun provideRegisterUseCase(repository: RegisterRepository) = RegisterUseCase(repository)
}