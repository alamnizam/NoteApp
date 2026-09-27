package com.codeturtle.notes.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeturtle.notes.common.token.TokenManager
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.common.utils.UiText
import com.codeturtle.notes.common.validation.ValidateConfirmPassword
import com.codeturtle.notes.common.validation.ValidateEmail
import com.codeturtle.notes.common.validation.ValidatePassword
import com.codeturtle.notes.common.validation.ValidateUsername
import com.codeturtle.notes.domain.model.RegisterRequest
import com.codeturtle.notes.domain.usecase.RegisterUseCase
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
    private val validateConfirmPassword: ValidateConfirmPassword,
    private val useCase: RegisterUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUIState())
    val uiState: StateFlow<RegistrationUIState> = _uiState

    private val _registerResponse = mutableStateOf(RegisterState())
    val registerResponse: State<RegisterState> = _registerResponse

    private val _loginClickEvent = Channel<LoginClickEvent>()
    val loginClickEvent = _loginClickEvent.receiveAsFlow()

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
            is RegistrationUIEvent.RegisterButtonClicked -> registerForm()

            is RegistrationUIEvent.LoginTextClicked -> {
                viewModelScope.launch {
                    _loginClickEvent.send(LoginClickEvent.Callback)
                }
            }
        }
    }

    private fun registerForm() {
        val formState = _uiState.value
        val validation = validateForm(formState)

        _uiState.value = formState.copy(
            userNameError = validation.userNameError,
            emailError = validation.emailError,
            passwordError = validation.passwordError,
            confirmPasswordError = validation.confirmPasswordError
        )

        if (validation.isValid) {
            registerUser(formState.toRegisterRequest())
        }
    }

    private fun validateForm(state: RegistrationUIState): RegistrationValidation {
        val userNameResult = validateUsername.execute(state.userName)
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val confirmPasswordResult =
            validateConfirmPassword.execute(state.password, state.confirmPassword)

        return RegistrationValidation(
            userNameError = userNameResult.errorMessage,
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage,
            confirmPasswordError = confirmPasswordResult.errorMessage,
            isValid = userNameResult.success &&
                emailResult.success &&
                passwordResult.success &&
                confirmPasswordResult.success
        )
    }

    private fun registerUser(
        request: RegisterRequest,
    ) {
        viewModelScope.launch {
            useCase(request).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _registerResponse.value = RegisterState(isLoading = true)
                    }

                    is Resource.Error -> {
                        _registerResponse.value =
                            RegisterState(errorMessage = resource.errorMessage.toString())
                    }

                    is Resource.Success -> {
                        resource.data?.let { response ->
                            tokenManager.saveToken(response.message)
                            tokenManager.saveIsLoggedIn(true)
                        }
                        _registerResponse.value = RegisterState(data = resource.data)
                    }

                    is Resource.DataError -> {
                        _registerResponse.value = RegisterState(errorData = resource.errorData)
                    }
                }
            }
        }
    }

    private fun RegistrationUIState.toRegisterRequest() = RegisterRequest(
        name = userName,
        email = email,
        password = password
    )

    private data class RegistrationValidation(
        val userNameError: UiText?,
        val emailError: UiText?,
        val passwordError: UiText?,
        val confirmPasswordError: UiText?,
        val isValid: Boolean
    )

    sealed class LoginClickEvent {
        data object Callback : LoginClickEvent()
    }
}
