package com.codeturtle.notes.common.utils

data class ValidationResult(
    val success: Boolean,
    val errorMessage: UiText? = null
)