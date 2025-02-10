package com.codeturtle.notes.authentication.login.data.repository

import com.codeturtle.notes.authentication.login.data.model.LoginRequest
import com.codeturtle.notes.authentication.login.data.network.LoginApiService
import com.codeturtle.notes.authentication.login.domain.model.LoginResponse
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

class LoginRepositoryImplShould {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var repository: LoginRepositoryImpl
    private lateinit var apiService: LoginApiService
    private val request: LoginRequest = mock()

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(LoginApiService::class.java)
        repository = LoginRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun returnSuccessResponseFromApiServiceWhenLoggedInSuccessfully() = runTest {
        val dataString = JsonFileReader.getJsonContent("auth_login_success.json")
        val data = Gson().fromJson(dataString, LoginResponse::class.java)
        val mockResponse = MockResponse()
        mockResponse.setBody(dataString)
        mockWebServer.enqueue(mockResponse)
        val result = repository.login(request)
        assertEquals(data.message, result.body()?.message)
    }

    @Test
    fun returnErrorResponseFromApiServiceWhenNotAbleLoggedInSuccessfully() = runTest {
        val dataString = JsonFileReader.getJsonContent("auth_login_failed.json")
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(400)
        mockResponse.setBody(dataString)
        mockWebServer.enqueue(mockResponse)
        val result = repository.login(request)
        assertEquals(400, result.code())
        assertEquals(dataString, result.errorBody()?.string())
    }

}