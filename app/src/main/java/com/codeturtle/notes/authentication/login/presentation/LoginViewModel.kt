package com.codeturtle.notes.authentication.login.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeturtle.notes.authentication.login.data.model.LoginRequest
import com.codeturtle.notes.authentication.login.domain.usecase.LoginUseCase
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.common.validation.ValidateEmail
import com.codeturtle.notes.common.validation.ValidateLoginPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validateLoginPassword: ValidateLoginPassword,
    private val useCase: LoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState

    private val _loginResponse = mutableStateOf(LoginState())
    val loginResponse: State<LoginState> = _loginResponse

    private val _registerClickEvent = Channel<RegisterClickEvent>()
    val registerClickEvent = _registerClickEvent.receiveAsFlow()

    private val _responseEvent = Channel<ResponseEvent>()
    val responseEvent = _responseEvent.receiveAsFlow()

    fun onEvent(uiEvent: LoginUIEvent){
        when(uiEvent){
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
        val emailResult = validateEmail.execute(_uiState.value.email)
        val passwordResult = validateLoginPassword.execute(_uiState.value.password)

        val hasError = listOf(
            emailResult, passwordResult
        ).any { !it.success }

        if (hasError) {
            _uiState.value = _uiState.value.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
        } else {
            _uiState.value = _uiState.value.copy(
                emailError = null,
                passwordError = null
            )
        }
        if(!hasError){
            val request = LoginRequest(
                email = _uiState.value.email,
                password = _uiState.value.password
            )
            loginUser(request)
            viewModelScope.launch {
                _responseEvent.send(ResponseEvent.Callback)
            }
        }
    }

    sealed class ResponseEvent {
        data object Callback : ResponseEvent()
    }

    private fun loginUser(request: LoginRequest) = viewModelScope.launch {
        useCase(request).onEach{
            when (it) {
                is Resource.Loading -> {
                    _loginResponse.value = LoginState(isLoading = true)
                }
                is Resource.Error -> {
                    _loginResponse.value = LoginState(errorMessage = it.errorMessage.toString())
                }
                is Resource.DataError -> {
                    _loginResponse.value = LoginState(errorData = it.errorData)
                }
                is Resource.Success -> {
                    _loginResponse.value = LoginState(data = it.data)
                }
            }
        }
    }

    sealed class RegisterClickEvent {
        data object Callback : RegisterClickEvent()
    }
}