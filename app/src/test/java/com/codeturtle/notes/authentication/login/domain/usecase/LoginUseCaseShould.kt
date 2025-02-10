package com.codeturtle.notes.authentication.login.domain.usecase

import com.codeturtle.notes.MainCoroutineRule
import com.codeturtle.notes.authentication.login.data.model.LoginRequest
import com.codeturtle.notes.authentication.login.domain.model.LoginResponse
import com.codeturtle.notes.authentication.login.domain.repository.LoginRepository
import com.codeturtle.notes.common.utils.Resource
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Response

class LoginUseCaseShould {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var userCase: LoginUseCase

    private val repository: LoginRepository = mock()
    private val data = mock<Response<LoginResponse>>()
    private val request: LoginRequest = mock()

    @Before
    fun setUp() {
        userCase = LoginUseCase(repository)
    }

    @Test
    fun returnSuccessWhenGotDataFromRepository() = runTest {
        whenever(repository.login(request)).thenReturn(data)
        var result: Resource<LoginResponse>? = null
        userCase(request).collect{
            result = it
        }
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals(data.body(), result?.data)
    }

    @Test
    fun returnErrorDataWhenNotAbleToGotDataFromRepository() = runTest {
        whenever(repository.login(request)).thenReturn(data)
        var result: Resource<LoginResponse>? = null
        userCase(request).collect{
            result = it
        }
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals(data.errorBody(), result?.errorData)
    }

    @Test
    fun returnErrorMessageDuringException() = runTest {
        whenever(repository.login(request)).thenThrow(
            java.lang.RuntimeException("Something went wrong")
        )
        var result: Resource<LoginResponse>? = null
        userCase(request).collect{
            result = it
        }
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals("Something went wrong", result?.errorMessage)
    }
}