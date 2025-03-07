package com.codeturtle.notes.common.di

import com.codeturtle.notes.common.validation.ValidateConfirmPassword
import com.codeturtle.notes.common.validation.ValidateEmail
import com.codeturtle.notes.common.validation.ValidateFieldNotEmpty
import com.codeturtle.notes.common.validation.ValidateLoginPassword
import com.codeturtle.notes.common.validation.ValidatePassword
import com.codeturtle.notes.common.validation.ValidateUsername
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ValidationModule {
    @Provides
    fun provideValidateUserName() = ValidateUsername()

    @Provides
    fun provideValidateEmail() = ValidateEmail()

    @Provides
    fun provideValidatePassword() = ValidatePassword()

    @Provides
    fun provideValidateLoginPassword() = ValidateLoginPassword()

    @Provides
    fun provideValidateConfirmPassword() = ValidateConfirmPassword()
    
    @Provides
    fun provideValidateFieldNotEmpty() = ValidateFieldNotEmpty()
}