package com.codeturtle.notes.authentication.login.presentation

import com.codeturtle.notes.authentication.login.domain.model.LoginResponse
import com.codeturtle.notes.common.utils.ErrorResponse

data class LoginState(
    val isLoading: Boolean = false,
    val data: LoginResponse? = null,
    val errorData: ErrorResponse? = null,
    val errorMessage: String = ""
)