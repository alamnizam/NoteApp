package com.codeturtle.notes.presentation

import com.codeturtle.notes.common.utils.ErrorResponse
import com.codeturtle.notes.domain.model.LoginResponse

data class LoginState(
    val isLoading: Boolean = false,
    val data: LoginResponse? = null,
    val errorData: ErrorResponse? = null,
    val errorMessage: String = ""
)