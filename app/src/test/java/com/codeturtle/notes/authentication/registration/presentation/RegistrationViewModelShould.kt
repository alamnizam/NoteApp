package com.codeturtle.notes.authentication.registration.presentation

import com.codeturtle.notes.authentication.registration.data.model.RegisterRequest
import com.codeturtle.notes.authentication.registration.data.model.RegisterResponse
import com.codeturtle.notes.authentication.registration.domain.use_case.RegisterUseCase
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidateConfirmPassword
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidateEmail
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidatePassword
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidateUsername
import com.codeturtle.notes.common.Resource
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Response

class RegistrationViewModelShould {
    private val useCase: RegisterUseCase = mock()
    private val validateUsername: ValidateUsername = mock()
    private val validateEmail: ValidateEmail = mock()
    private val validatePassword: ValidatePassword = mock()
    private val validateConfirmPassword: ValidateConfirmPassword = mock()
    private val viewModel = RegistrationViewModel(
        validateUsername,
        validateEmail,
        validatePassword,
        validateConfirmPassword,
        useCase
    )

    private val data : Response<RegisterResponse> = mock()

    @Test
    fun validateUiStateUserNameChanged() {
        viewModel.onEvent(RegistrationUIEvent.UserNameChanged("Nizam"))
        assertEquals("Nizam", viewModel.uiState.value.userName)
    }

    @Test
    fun validateUiStateEmailChanged() {
        viewModel.onEvent(RegistrationUIEvent.EmailChanged("alamnizam1992@gmail.com"))
        assertEquals("alamnizam1992@gmail.com", viewModel.uiState.value.email)
    }

    @Test
    fun validateUiStatePasswordChanged() {
        viewModel.onEvent(RegistrationUIEvent.PasswordChanged("Nizam@123"))
        assertEquals("Nizam@123", viewModel.uiState.value.password)
    }

    @Test
    fun validateUiStateConfirmPasswordChanged() {
        viewModel.onEvent(RegistrationUIEvent.ConfirmPasswordChanged("Nizam@123"))
        assertEquals("Nizam@123", viewModel.uiState.value.confirmPassword)
    }

    @Test
    fun validateSuccessStateUserRegistration()= runTest{
        viewModel.onEvent(RegistrationUIEvent.UserNameChanged("Nizam"))
        viewModel.onEvent(RegistrationUIEvent.EmailChanged("alamnizam1992@gmail.com"))
        viewModel.onEvent(RegistrationUIEvent.PasswordChanged("Nizam@123"))
        viewModel.onEvent(RegistrationUIEvent.ConfirmPasswordChanged("Nizam@123"))
        val request = RegisterRequest(
            name = viewModel.uiState.value.userName,
            email = viewModel.uiState.value.email,
            password = viewModel.uiState.value.password
        )
        whenever(useCase.invoke(request = request)).thenReturn(
            flow {
                emit(Resource.Success(data = data))
            }
        )

        assertEquals(data,viewModel.registerResponse.value.data)
    }
}