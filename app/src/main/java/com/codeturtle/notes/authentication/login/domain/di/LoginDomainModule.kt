package com.codeturtle.notes.authentication.login.domain.di

import com.codeturtle.notes.authentication.login.domain.repository.LoginRepository
import com.codeturtle.notes.authentication.login.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object LoginDomainModule {
    @Provides
    fun provideLoginUseCase(repository: LoginRepository) = LoginUseCase(repository)
}