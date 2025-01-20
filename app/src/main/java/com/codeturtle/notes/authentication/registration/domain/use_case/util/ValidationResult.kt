package com.codeturtle.notes.authentication.registration.domain.use_case.util

import com.codeturtle.notes.common.uits.UiText

data class ValidationResult(
    val success: Boolean,
    val errorMessage: UiText? = null
)