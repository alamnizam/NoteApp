package com.codeturtle.notes.authentication.registration.domain.use_case.validation

import android.util.Patterns
import com.codeturtle.notes.R
import com.codeturtle.notes.authentication.registration.domain.use_case.util.ValidationResult
import com.codeturtle.notes.common.uits.UiText

class ValidateEmail {
    fun execute(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult(
                success = false,
                errorMessage = UiText.StringResource(R.string.the_email_can_t_be_blank)
            )

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResult(
                success = false,
                errorMessage = UiText.StringResource(R.string.that_s_not_a_valid_email)
            )

            else -> ValidationResult(success = true)
        }
    }
}