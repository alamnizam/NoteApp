package com.codeturtle.notes.authentication.registration.presentation

import com.codeturtle.notes.MainCoroutineRule
import com.codeturtle.notes.authentication.registration.data.model.RegisterRequest
import com.codeturtle.notes.authentication.registration.data.model.RegisterResponse
import com.codeturtle.notes.authentication.registration.domain.use_case.RegisterUseCase
import com.codeturtle.notes.authentication.registration.domain.use_case.util.ValidationResult
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidateConfirmPassword
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidateEmail
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidatePassword
import com.codeturtle.notes.authentication.registration.domain.use_case.validation.ValidateUsername
import com.codeturtle.notes.common.utils.Resource
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Response

class RegistrationViewModelShould {
    private lateinit var viewModel: RegistrationViewModel

    private val validateEmail: ValidateEmail = mock()
    private val validatePassword: ValidatePassword = mock()
    private val validateConfirmPassword: ValidateConfirmPassword = mock()

    private val validateUsername: ValidateUsername = mock()
    private val data: Response<RegisterResponse> = mock()
    private val useCase: RegisterUseCase = mock()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setup() {
        viewModel = RegistrationViewModel(
            validateUsername,
            validateEmail,
            validatePassword,
            validateConfirmPassword,
            useCase
        )
    }

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
    fun validateSuccessStateUserRegistration() = runTest {
        whenever(validateUsername.execute(username = "Nizam")).thenReturn(ValidationResult(true))
        whenever(validateEmail.execute(email = "alamnizam1992@gmail.com")).thenReturn(
            ValidationResult(true)
        )
        whenever(validatePassword.execute(password = "Nizam@123")).thenReturn(ValidationResult(true))
        whenever(
            validateConfirmPassword.execute(
                password = "Nizam@123",
                confirmPassword = "Nizam@123"
            )
        ).thenReturn(
            ValidationResult(true)
        )
        viewModel.onEvent(RegistrationUIEvent.UserNameChanged("Nizam"))
        viewModel.onEvent(RegistrationUIEvent.EmailChanged("alamnizam1992@gmail.com"))
        viewModel.onEvent(RegistrationUIEvent.PasswordChanged("Nizam@123"))
        viewModel.onEvent(RegistrationUIEvent.ConfirmPasswordChanged("Nizam@123"))
        val request = RegisterRequest(
            name = viewModel.uiState.value.userName,
            email = viewModel.uiState.value.email,
            password = viewModel.uiState.value.password
        )
        whenever(useCase(request)).thenReturn(
            flow {
                emit(Resource.Success(data = data))
            }
        )
        viewModel.onEvent(RegistrationUIEvent.RegisterButtonClicked)
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals(data, viewModel.registerResponse.value.data)
    }

    @Test
    fun validateErrorStateUserRegistration() = runTest {
        whenever(validateUsername.execute(username = "Nizam")).thenReturn(ValidationResult(true))
        whenever(validateEmail.execute(email = "alamnizam1992@gmail.com")).thenReturn(
            ValidationResult(true)
        )
        whenever(validatePassword.execute(password = "Nizam@123")).thenReturn(ValidationResult(true))
        whenever(
            validateConfirmPassword.execute(
                password = "Nizam@123",
                confirmPassword = "Nizam@123"
            )
        ).thenReturn(ValidationResult(true))
        viewModel.onEvent(RegistrationUIEvent.UserNameChanged("Nizam"))
        viewModel.onEvent(RegistrationUIEvent.EmailChanged("alamnizam1992@gmail.com"))
        viewModel.onEvent(RegistrationUIEvent.PasswordChanged("Nizam@123"))
        viewModel.onEvent(RegistrationUIEvent.ConfirmPasswordChanged("Nizam@123"))
        val request = RegisterRequest(
            name = viewModel.uiState.value.userName,
            email = viewModel.uiState.value.email,
            password = viewModel.uiState.value.password
        )
        whenever(useCase(request)).thenReturn(
            flow {
                emit(Resource.Error(error = "Something went wrong"))
            }
        )
        viewModel.onEvent(RegistrationUIEvent.RegisterButtonClicked)
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals("Something went wrong", viewModel.registerResponse.value.errorMessage)
    }
}