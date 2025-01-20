package com.codeturtle.notes.authentication.registration.domain.use_case.validation

import com.codeturtle.notes.R
import com.codeturtle.notes.authentication.registration.domain.use_case.util.ValidationResult
import com.codeturtle.notes.common.uits.UiText

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