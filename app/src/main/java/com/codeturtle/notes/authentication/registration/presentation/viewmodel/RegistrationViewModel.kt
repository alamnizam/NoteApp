package com.codeturtle.notes.authentication.registration.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.codeturtle.notes.authentication.registration.presentation.event.RegistrationUIEvent
import com.codeturtle.notes.authentication.registration.presentation.state.RegistrationUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegistrationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationUIState())
    val uiState: StateFlow<RegistrationUIState> = _uiState

    fun onEvent(uiEvent: RegistrationUIEvent) {
        when (uiEvent) {
            is RegistrationUIEvent.UserNameChanged -> {
                _uiState.value = _uiState.value.copy(
                    userName = uiEvent.userName
                )
            }

            is RegistrationUIEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(
                    email = uiEvent.email
                )
            }

            is RegistrationUIEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(
                    password = uiEvent.password
                )
            }

            is RegistrationUIEvent.ConfirmPasswordChanged -> {
                _uiState.value = _uiState.value.copy(
                    confirmPassword = uiEvent.confirmPassword
                )
            }

            else -> {

            }
        }
    }
}