package com.codeturtle.notes.authentication.login.data.repository

import com.codeturtle.notes.authentication.login.data.model.LoginRequest
import com.codeturtle.notes.authentication.login.data.network.LoginApiService
import com.codeturtle.notes.authentication.login.domain.model.LoginResponse
import com.codeturtle.notes.authentication.login.domain.repository.LoginRepository
import retrofit2.Response

class LoginRepositoryImpl(
    private val apiService: LoginApiService
) : LoginRepository {
    override suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return apiService.login(request)
    }
}