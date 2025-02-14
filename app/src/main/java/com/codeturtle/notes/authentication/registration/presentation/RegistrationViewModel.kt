package com.codeturtle.notes.authentication.registration.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeturtle.notes.authentication.registration.data.model.RegisterRequest
import com.codeturtle.notes.authentication.registration.domain.use_case.RegisterUseCase
import com.codeturtle.notes.common.preference.tokken.TokenManager
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.common.validation.ValidateConfirmPassword
import com.codeturtle.notes.common.validation.ValidateEmail
import com.codeturtle.notes.common.validation.ValidatePassword
import com.codeturtle.notes.common.validation.ValidateUsername
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val validateUsername: ValidateUsername,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateConfirmPassword: ValidateConfirmPassword,
    private val useCase: RegisterUseCase
) : ViewModel() {

    @Inject
    lateinit var tokenManager: TokenManager

    private val _uiState = MutableStateFlow(RegistrationUIState())
    val uiState: StateFlow<RegistrationUIState> = _uiState

    private val _registerResponse = mutableStateOf(RegisterState())
    val registerResponse: State<RegisterState> = _registerResponse

    private val _responseEvent = Channel<ResponseEvent>()
    val responseEvent = _responseEvent.receiveAsFlow()

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
        val userNameResult = validateUsername.execute(_uiState.value.userName)
        val emailResult = validateEmail.execute(_uiState.value.email)
        val passwordResult = validatePassword.execute(_uiState.value.password)
        val confirmPasswordResult =
            validateConfirmPassword.execute(_uiState.value.password, _uiState.value.confirmPassword)

        val hasError = listOf(
            userNameResult, emailResult, passwordResult, confirmPasswordResult
        ).any { !it.success }

        if (hasError) {
            _uiState.value = _uiState.value.copy(
                userNameError = userNameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                confirmPasswordError = confirmPasswordResult.errorMessage
            )
        } else {
            _uiState.value = _uiState.value.copy(
                userNameError = null,
                emailError = null,
                passwordError = null,
                confirmPasswordError = null
            )
        }

        if (!hasError) {
            val request = RegisterRequest(
                name = _uiState.value.userName,
                email = _uiState.value.email,
                password = _uiState.value.password
            )
            registerUser(request)
        }
        viewModelScope.launch {
            _responseEvent.send(ResponseEvent.Callback)
        }
    }

    private fun registerUser(
        request: RegisterRequest,
    ) = viewModelScope.launch {
        useCase(request).onEach {
            when (it) {
                is Resource.Loading -> {
                    _registerResponse.value = RegisterState(isLoading = true)
                }

                is Resource.Error -> {
                    _registerResponse.value = RegisterState(errorMessage = it.errorMessage.toString())
                }

                is Resource.Success -> {
                    _registerResponse.value = RegisterState(data = it.data)
                }

                is Resource.DataError -> {
                    _registerResponse.value = RegisterState(errorData = it.errorData)
                }
            }
        }.launchIn(viewModelScope)
    }

    sealed class ResponseEvent {
        data object Callback : ResponseEvent()
    }

    sealed class LoginClickEvent {
        data object Callback : LoginClickEvent()
    }
}