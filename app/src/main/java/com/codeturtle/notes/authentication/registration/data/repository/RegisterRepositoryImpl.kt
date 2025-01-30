package com.codeturtle.notes.authentication.registration.data.repository

import com.codeturtle.notes.authentication.registration.data.model.RegisterRequest
import com.codeturtle.notes.authentication.registration.data.model.RegisterResponse
import com.codeturtle.notes.authentication.registration.data.network.RegisterApiService
import com.codeturtle.notes.authentication.registration.domain.repository.RegisterRepository
import retrofit2.Response

class RegisterRepositoryImpl(
    private val apiService: RegisterApiService
) : RegisterRepository {
    override suspend fun register(request: RegisterRequest): Response<RegisterResponse> {
        return apiService.register(request)
    }

}