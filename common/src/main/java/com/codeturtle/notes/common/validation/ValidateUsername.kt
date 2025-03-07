package com.codeturtle.notes.common.validation

import com.codeturtle.notes.common.R
import com.codeturtle.notes.common.utils.UiText
import com.codeturtle.notes.common.utils.ValidationResult

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