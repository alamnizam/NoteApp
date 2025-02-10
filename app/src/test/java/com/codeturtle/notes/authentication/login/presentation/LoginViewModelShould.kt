package com.codeturtle.notes.authentication.login.presentation

import com.codeturtle.notes.MainCoroutineRule
import com.codeturtle.notes.authentication.login.data.model.LoginRequest
import com.codeturtle.notes.authentication.login.domain.model.LoginResponse
import com.codeturtle.notes.authentication.login.domain.usecase.LoginUseCase
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.common.utils.ValidationResult
import com.codeturtle.notes.common.validation.ValidateEmail
import com.codeturtle.notes.common.validation.ValidateLoginPassword
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class LoginViewModelShould {
    private lateinit var viewModel: LoginViewModel

    private val validateEmail: ValidateEmail = mock()
    private val validateLoginPassword: ValidateLoginPassword = mock()
    private val data: LoginResponse = mock()
    private val useCase: LoginUseCase = mock()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setUp() {
        viewModel = LoginViewModel(validateEmail, validateLoginPassword, useCase)
    }

    @Test
    fun validateUiStateEmailChanged() {
        viewModel.onEvent(LoginUIEvent.EmailChanged("alamnizam1992@gmail.com"))
        assertEquals("alamnizam1992@gmail.com", viewModel.uiState.value.email)
    }

    @Test
    fun validateUiStatePasswordChanged() {
        viewModel.onEvent(LoginUIEvent.PasswordChanged("Nizam@123"))
        assertEquals("Nizam@123", viewModel.uiState.value.password)
    }

    @Test
    fun validateSuccessStateUserLogin() = runTest {
        whenever(validateEmail.execute(email = "alamnizam1992@gmail.com")).thenReturn(
            ValidationResult(true)
        )
        whenever(validateLoginPassword.execute(password = "Nizam@123")).thenReturn(
            ValidationResult(true)
        )
        viewModel.onEvent(LoginUIEvent.EmailChanged("alamnizam1992@gmail.com"))
        viewModel.onEvent(LoginUIEvent.PasswordChanged("Nizam@123"))
        val request = LoginRequest(
            email = viewModel.uiState.value.email,
            password = viewModel.uiState.value.password
        )
        whenever(useCase(request)).thenReturn(
            flow {
                emit(Resource.Success(data = data))
            }
        )
        viewModel.onEvent(LoginUIEvent.LoginButtonClicked)
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals(data, viewModel.loginResponse.value.data)
    }

    @Test
    fun validateErrorStateUserLogin() = runTest {
        whenever(validateEmail.execute(email = "alamnizam1992@gmail.com")).thenReturn(
            ValidationResult(true)
        )
        whenever(validateLoginPassword.execute(password = "Nizam@123")).thenReturn(ValidationResult(true))
        viewModel.onEvent(LoginUIEvent.EmailChanged("alamnizam1992@gmail.com"))
        viewModel.onEvent(LoginUIEvent.PasswordChanged("Nizam@123"))
        val request = LoginRequest(
            email = viewModel.uiState.value.email,
            password = viewModel.uiState.value.password
        )
        whenever(useCase(request)).thenReturn(
            flow {
                emit(Resource.Error(error = "Something went wrong"))
            }
        )
        viewModel.onEvent(LoginUIEvent.LoginButtonClicked)
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals("Something went wrong", viewModel.loginResponse.value.errorMessage)
    }
}