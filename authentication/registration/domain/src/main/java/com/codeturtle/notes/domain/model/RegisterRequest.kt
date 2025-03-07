package com.codeturtle.notes.domain.model

data class RegisterRequest(
    val email: String,
    val name: String,
    val password: String
)