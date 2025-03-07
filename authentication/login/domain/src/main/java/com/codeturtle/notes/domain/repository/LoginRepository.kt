package com.codeturtle.notes.domain.repository

import com.codeturtle.notes.domain.model.LoginRequest
import com.codeturtle.notes.domain.model.LoginResponse
import retrofit2.Response

interface LoginRepository {
    suspend fun login(request: LoginRequest): Response<LoginResponse>
}