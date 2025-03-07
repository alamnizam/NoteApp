package com.codeturtle.notes.presentation

import com.codeturtle.notes.common.utils.UiText

data class LoginUIState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
)