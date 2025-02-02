package com.codeturtle.notes.authentication.registration.domain.use_case

import com.codeturtle.notes.MainCoroutineRule
import com.codeturtle.notes.authentication.registration.data.model.RegisterRequest
import com.codeturtle.notes.authentication.registration.data.model.RegisterResponse
import com.codeturtle.notes.authentication.registration.domain.repository.RegisterRepository
import com.codeturtle.notes.common.Resource
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Response

class RegisterUseCaseShould {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var userCase: RegisterUseCase

    private val repository: RegisterRepository = mock()
    private val data = mock<Response<RegisterResponse>>()
    private val request:RegisterRequest = mock()

    @Before
    fun setUp() {
        userCase = RegisterUseCase(repository)
    }

    @Test
    fun returnSuccessWhenGotDataFromRepository() = runTest {
        whenever(repository.register(request)).thenReturn(data)
        var result:Resource<Response<RegisterResponse>>? = null
        userCase(request).collect{
            result = it
        }
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals(data, result?.data)
    }

    @Test
    fun returnErrorMessageDuringException() = runTest {
        whenever(repository.register(request)).thenThrow(
            java.lang.RuntimeException("Something went wrong")
        )
        var result:Resource<Response<RegisterResponse>>? = null
        userCase(request).collect{
            result = it
        }
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals("Something went wrong", result?.error)
    }
}