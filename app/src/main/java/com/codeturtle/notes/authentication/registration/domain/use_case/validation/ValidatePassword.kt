package com.codeturtle.notes.authentication.registration.domain.use_case.validation

import com.codeturtle.notes.R
import com.codeturtle.notes.authentication.registration.domain.use_case.util.ValidationResult
import com.codeturtle.notes.common.uits.UiText

class ValidatePassword {
    fun execute(password: String): ValidationResult {
        val containLettersAndDigitsAndSpecialCharacters = password.any { it.isDigit() } && password.any { it.isLetter() } && password.any { !it.isLetterOrDigit() }
        return when {
            password.isBlank() -> ValidationResult(
                success = false,
                errorMessage = UiText.StringResource(R.string.the_password_can_t_be_blank)
            )
            password.length < 8 -> ValidationResult(
                success = false,
                errorMessage = UiText.StringResource(R.string.the_password_need_to_consist_of_at_least_8_characters)
            )
            !containLettersAndDigitsAndSpecialCharacters -> ValidationResult(
                success = false,
                errorMessage = UiText.StringResource(R.string.the_password_must_have_1_special_character_1_number_1_uppercase_and_1_lowercase_character)
            )
            else -> ValidationResult(success = true)
        }
    }
}
