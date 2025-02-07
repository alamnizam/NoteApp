package com.codeturtle.notes.authentication.registration.presentation

import com.codeturtle.notes.authentication.registration.domain.model.RegisterResponse
import com.codeturtle.notes.common.utils.ErrorResponse

data class RegisterState(
    val isLoading: Boolean = false,
    val data: RegisterResponse? = null,
    val errorData: ErrorResponse? = null,
    val errorMessage: String = ""
)
