package com.codeturtle.notes.domain.usecase

import com.codeturtle.notes.common.utils.ErrorResponse
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.common.utils.safeApiCall
import com.codeturtle.notes.domain.model.LoginRequest
import com.codeturtle.notes.domain.model.LoginResponse
import com.codeturtle.notes.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val repository: LoginRepository
) {
    operator fun invoke(request: LoginRequest): Flow<Resource<LoginResponse, ErrorResponse>> =
        safeApiCall { repository.login(request) }
}