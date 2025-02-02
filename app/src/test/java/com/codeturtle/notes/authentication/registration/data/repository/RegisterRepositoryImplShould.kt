package com.codeturtle.notes.authentication.registration.data.repository

import com.codeturtle.notes.authentication.registration.data.model.RegisterRequest
import com.codeturtle.notes.authentication.registration.data.model.RegisterResponse
import com.codeturtle.notes.authentication.registration.data.network.RegisterApiService
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Response

class RegisterRepositoryImplShould{
    private lateinit var repository: RegisterRepositoryImpl
    private val apiService: RegisterApiService = mock()
    private val request: RegisterRequest = mock()
    private val response: Response<RegisterResponse> = mock()

    @Before
    fun setup(){
        repository = RegisterRepositoryImpl(apiService)
    }

    @Test
    fun returnResponseFromApiService() = runTest{
        whenever(apiService.register(request)).thenReturn(response)
        val result = repository.register(request)
        assertEquals(response, result)
    }
}