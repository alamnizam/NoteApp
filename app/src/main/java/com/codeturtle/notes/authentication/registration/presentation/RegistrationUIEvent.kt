package com.codeturtle.notes.authentication.registration.presentation

sealed class RegistrationUIEvent {
    data class UserNameChanged(val userName: String) : RegistrationUIEvent()
    data class EmailChanged(val email: String) : RegistrationUIEvent()
    data class PasswordChanged(val password: String) : RegistrationUIEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegistrationUIEvent()
    data object RegisterButtonClicked : RegistrationUIEvent()
    data object LoginTextClicked : RegistrationUIEvent()
}