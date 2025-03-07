package com.codeturtle.notes.data.repository

import com.codeturtle.notes.data.network.LoginApiService
import com.codeturtle.notes.domain.model.LoginRequest
import com.codeturtle.notes.domain.model.LoginResponse
import com.codeturtle.notes.domain.repository.LoginRepository
import retrofit2.Response

class LoginRepositoryImpl(
    private val apiService: LoginApiService
) : LoginRepository {
    override suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return apiService.login(request)
    }
}