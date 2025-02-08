package com.codeturtle.notes.common.validation

import com.codeturtle.notes.R
import com.codeturtle.notes.common.utils.UiText
import com.codeturtle.notes.common.utils.ValidationResult

class ValidateConfirmPassword {
    fun execute(password: String,confirmPassword: String): ValidationResult {
        return when {
            confirmPassword.isBlank() -> ValidationResult(
                success = false,
                errorMessage = UiText.StringResource(R.string.the_confirm_password_can_t_be_blank)
            )
            password != confirmPassword -> ValidationResult(
                success = false,
                errorMessage = UiText.StringResource(R.string.the_passwords_don_t_match)
            )
            else -> ValidationResult(success = true)
        }
    }
}