package com.codeturtle.notes.data.repository

import com.codeturtle.notes.domain.model.RegisterRequest
import com.codeturtle.notes.data.network.RegisterApiService
import com.codeturtle.notes.domain.model.RegisterResponse
import com.codeturtle.notes.domain.repository.RegisterRepository
import retrofit2.Response

class RegisterRepositoryImpl(
    private val apiService: RegisterApiService
) : RegisterRepository {
    override suspend fun register(request: RegisterRequest): Response<RegisterResponse> {
        return apiService.register(request)
    }
}