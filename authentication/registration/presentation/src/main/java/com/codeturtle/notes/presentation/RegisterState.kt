package com.codeturtle.notes.presentation

import com.codeturtle.notes.common.utils.ErrorResponse
import com.codeturtle.notes.domain.model.RegisterResponse

data class RegisterState(
    val isLoading: Boolean = false,
    val data: RegisterResponse? = null,
    val errorData: ErrorResponse? = null,
    val errorMessage: String = ""
)
