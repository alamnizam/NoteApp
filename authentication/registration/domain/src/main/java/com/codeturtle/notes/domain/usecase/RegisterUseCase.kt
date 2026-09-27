package com.codeturtle.notes.domain.usecase

import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.common.utils.safeApiCall
import com.codeturtle.notes.domain.model.RegisterRequest
import com.codeturtle.notes.domain.model.RegisterResponse
import com.codeturtle.notes.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(
    private val repository: RegisterRepository
) {
    operator fun invoke(request: RegisterRequest): Flow<Resource<RegisterResponse>> =
        safeApiCall { repository.register(request) }
}
