package com.codeturtle.notes.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeturtle.notes.common.token.TokenManager
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.common.utils.UiText
import com.codeturtle.notes.common.validation.ValidateEmail
import com.codeturtle.notes.common.validation.ValidateLoginPassword
import com.codeturtle.notes.domain.model.LoginRequest
import com.codeturtle.notes.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validateLoginPassword: ValidateLoginPassword,
    private val useCase: LoginUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState

    private val _loginResponse = mutableStateOf(LoginState())
    val loginResponse: State<LoginState> = _loginResponse

    private val _registerClickEvent = Channel<RegisterClickEvent>()
    val registerClickEvent = _registerClickEvent.receiveAsFlow()

    fun onEvent(uiEvent: LoginUIEvent) {
        when (uiEvent) {
            is LoginUIEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(
                    email = uiEvent.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(
                    password = uiEvent.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> loginForm()

            is LoginUIEvent.RegisterTextClicked -> {
                viewModelScope.launch {
                    _registerClickEvent.send(RegisterClickEvent.Callback)
                }
            }
        }
    }

    private fun loginForm() {
        val formState = _uiState.value
        val validation = validateForm(formState)

        updateValidationState(formState, validation)

        if (validation.isValid) {
            loginUser(formState.toLoginRequest())
        }
    }

    private fun validateForm(state: LoginUIState): LoginValidation {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validateLoginPassword.execute(state.password)

        return LoginValidation(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage,
            isValid = emailResult.success && passwordResult.success
        )
    }

    private fun updateValidationState(
        state: LoginUIState,
        validation: LoginValidation
    ) {
        _uiState.value = state.copy(
            emailError = validation.emailError,
            passwordError = validation.passwordError
        )
    }

    private fun loginUser(request: LoginRequest) {
        viewModelScope.launch {
            useCase(request).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _loginResponse.value = LoginState(isLoading = true)
                    is Resource.Error -> {
                        _loginResponse.value =
                            LoginState(errorMessage = resource.errorMessage.toString())
                    }
                    is Resource.DataError -> {
                        _loginResponse.value = LoginState(errorData = resource.errorData)
                    }
                    is Resource.Success -> {
                        resource.data?.let { response ->
                            tokenManager.saveToken(response.message)
                            tokenManager.saveIsLoggedIn(true)
                        }
                        _loginResponse.value = LoginState(data = resource.data)
                    }
                }
            }
        }
    }

    private fun LoginUIState.toLoginRequest() = LoginRequest(
        email = email,
        password = password
    )

    private data class LoginValidation(
        val emailError: UiText?,
        val passwordError: UiText?,
        val isValid: Boolean
    )

    sealed class RegisterClickEvent {
        data object Callback : RegisterClickEvent()
    }
}
