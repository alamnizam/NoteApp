package com.codeturtle.notes.authentication.registration.data.model

data class RegisterRequest(
    val email: String,
    val name: String,
    val password: String
)