package com.codeturtle.notes.authentication.registration.presentation

import com.codeturtle.notes.authentication.registration.data.model.RegisterResponse
import retrofit2.Response

data class RegisterState(
    val isLoading: Boolean = false,
    val data: Response<RegisterResponse>? = null,
    val errorData: RegisterResponse? = null,
    val errorMessage: String = ""
)
