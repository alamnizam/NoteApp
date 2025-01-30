package com.codeturtle.notes.authentication.registration.domain.di

import com.codeturtle.notes.authentication.registration.domain.repository.RegisterRepository
import com.codeturtle.notes.authentication.registration.domain.use_case.RegisterUseCase
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidateConfirmPassword
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidateEmail
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidatePassword
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidateUsername
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RegisterModule {
    @Provides
    fun provideValidateUserName() = ValidateUsername()

    @Provides
    fun provideValidateEmail() = ValidateEmail()

    @Provides
    fun provideValidatePassword() = ValidatePassword()

    @Provides
    fun provideValidateConfirmPassword() = ValidateConfirmPassword()

    @Provides
    fun provideRegisterUseCase(repository: RegisterRepository) = RegisterUseCase(repository)
}