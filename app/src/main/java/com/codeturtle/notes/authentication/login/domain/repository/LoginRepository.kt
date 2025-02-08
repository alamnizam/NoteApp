package com.codeturtle.notes.authentication.login.domain.repository

import com.codeturtle.notes.authentication.login.data.model.LoginRequest
import com.codeturtle.notes.authentication.login.domain.model.LoginResponse
import retrofit2.Response

interface LoginRepository {
    suspend fun login(request: LoginRequest): Response<LoginResponse>
}