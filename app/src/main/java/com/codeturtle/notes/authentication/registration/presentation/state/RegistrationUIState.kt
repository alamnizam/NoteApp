package com.codeturtle.notes.authentication.registration.presentation.state

data class RegistrationUIState(
    val userName: String = "",
    val userNameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val isLoading: Boolean = false,
)