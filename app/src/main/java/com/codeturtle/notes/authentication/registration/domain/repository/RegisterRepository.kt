package com.codeturtle.notes.authentication.registration.domain.repository

import com.codeturtle.notes.authentication.registration.data.model.RegisterRequest
import com.codeturtle.notes.authentication.registration.domain.model.RegisterResponse
import retrofit2.Response

interface RegisterRepository {
    suspend fun register(request: RegisterRequest): Response<RegisterResponse>
}