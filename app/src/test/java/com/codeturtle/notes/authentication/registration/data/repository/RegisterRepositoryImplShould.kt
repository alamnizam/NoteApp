package com.codeturtle.notes.authentication.registration.data.repository

import com.codeturtle.notes.authentication.registration.data.model.RegisterRequest
import com.codeturtle.notes.authentication.registration.data.network.RegisterApiService
import com.codeturtle.notes.authentication.registration.domain.model.RegisterResponse
import com.codeturtle.notes.util.JsonFileReader
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterRepositoryImplShould {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var repository: RegisterRepositoryImpl
    private lateinit var apiService: RegisterApiService
    private val request: RegisterRequest = mock()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RegisterApiService::class.java)
        repository = RegisterRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun returnSuccessResponseFromApiService() = runTest {
        val dataString = JsonFileReader.getJsonContent("auth_register_success.json")
        val data = Gson().fromJson(dataString, RegisterResponse::class.java)
        val mockResponse = MockResponse()
        mockResponse.setBody(dataString)
        mockWebServer.enqueue(mockResponse)
        val result = repository.register(request)
        assertEquals(data, result.body())
    }

    @Test
    fun returnErrorResponseFromApiService() = runTest {
        val dataString = JsonFileReader.getJsonContent("auth_register_email_error.json")
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(409)
        mockResponse.setBody(dataString)
        mockWebServer.enqueue(mockResponse)
        val result = repository.register(request)
        assertEquals(409, result.code())
        assertEquals(dataString, result.errorBody()?.string())
    }
}