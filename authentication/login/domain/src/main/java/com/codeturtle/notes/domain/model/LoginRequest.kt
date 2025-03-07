package com.codeturtle.notes.domain.model

data class LoginRequest(
    val email: String,
    val password: String
)