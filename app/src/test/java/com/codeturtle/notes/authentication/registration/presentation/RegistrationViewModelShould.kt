package com.codeturtle.notes.authentication.registration.presentation

import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidateConfirmPassword
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidateEmail
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidatePassword
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidateUsername
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock

class RegistrationViewModelShould {
    private val validateUsername: ValidateUsername = mock()
    private val validateEmail: ValidateEmail = mock()
    private val validatePassword: ValidatePassword = mock()
    private val validateConfirmPassword: ValidateConfirmPassword = mock()
    private val viewModel = RegistrationViewModel(
        validateUsername,
        validateEmail,
        validatePassword,
        validateConfirmPassword
    )
//    private val repo: RegistrationRepository = mock()

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

//    @Test
//    fun validateSuccessStateUserRegistration()= runTest{
//        whenever()
//    }
}