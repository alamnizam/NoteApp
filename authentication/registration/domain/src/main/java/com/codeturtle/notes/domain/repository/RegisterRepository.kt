package com.codeturtle.notes.domain.repository

import com.codeturtle.notes.domain.model.RegisterRequest
import com.codeturtle.notes.domain.model.RegisterResponse
import retrofit2.Response

interface RegisterRepository {
    suspend fun register(request: RegisterRequest): Response<RegisterResponse>
}