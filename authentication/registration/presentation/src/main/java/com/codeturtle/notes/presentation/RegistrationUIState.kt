package com.codeturtle.notes.presentation

import com.codeturtle.notes.common.utils.UiText

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