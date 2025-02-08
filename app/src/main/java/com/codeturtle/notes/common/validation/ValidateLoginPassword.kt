package com.codeturtle.notes.common.validation

import com.codeturtle.notes.R
import com.codeturtle.notes.common.utils.UiText
import com.codeturtle.notes.common.utils.ValidationResult

class ValidateLoginPassword {
    fun execute(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult(
                success = false,
                errorMessage = UiText.StringResource(R.string.the_password_can_t_be_blank)
            )
            else -> ValidationResult(success = true)
        }
    }
}
