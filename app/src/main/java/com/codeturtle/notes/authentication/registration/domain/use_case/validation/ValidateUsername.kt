package com.codeturtle.notes.authentication.registration.domain.use_case.validation

import com.codeturtle.notes.R
import com.codeturtle.notes.authentication.registration.domain.use_case.util.ValidationResult
import com.codeturtle.notes.common.uits.UiText

class ValidateUsername {
    fun execute(username: String): ValidationResult {
        return when {
            username.isBlank() -> ValidationResult(
                success = false,
                errorMessage = UiText.StringResource(R.string.the_username_can_t_be_blank)
            )

            else -> ValidationResult(success = true)
        }
    }
}