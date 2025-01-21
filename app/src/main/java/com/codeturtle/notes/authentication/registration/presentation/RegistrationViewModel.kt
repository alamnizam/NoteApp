package com.codeturtle.notes.authentication.registration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidateConfirmPassword
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidateEmail
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidatePassword
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidateUsername
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val validateUsername: ValidateUsername,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateConfirmPassword: ValidateConfirmPassword
): ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationUIState())
    val uiState: StateFlow<RegistrationUIState> = _uiState

    private val _validationEventChanel = Channel<ValidationEvent>()
    val validationEventChanel = _validationEventChanel.receiveAsFlow()

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

            is RegistrationUIEvent.RegisterButtonClicked -> {
                registerForm()
            }
            else -> {}
        }
    }

    private fun registerForm() {
        val userNameResult = validateUsername.execute(_uiState.value.userName)
        val emailResult = validateEmail.execute(_uiState.value.email)
        val passwordResult = validatePassword.execute(_uiState.value.password)
        val confirmPasswordResult = validateConfirmPassword.execute(_uiState.value.password, _uiState.value.confirmPassword)

        val hasError = listOf(
            userNameResult,
            emailResult,
            passwordResult,
            confirmPasswordResult
        ).any { !it.success}

        if(hasError) {
            _uiState.value = _uiState.value.copy(
                userNameError = userNameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                confirmPasswordError = confirmPasswordResult.errorMessage
            )
        }else{
            _uiState.value = _uiState.value.copy(
                userNameError = null,
                emailError = null,
                passwordError = null,
                confirmPasswordError = null
            )
            viewModelScope.launch {
                _validationEventChanel.send(ValidationEvent.Success)
            }
        }

    }

    sealed class ValidationEvent{
        data object Success: ValidationEvent()
    }
}