package com.codeturtle.notes.authentication.registration.presentation

import com.codeturtle.notes.common.uits.UiText

data class RegistrationUIState(
    val userName: String = "",
    val userNameError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: UiText? = null
)