package com.codeturtle.notes.authentication.registration.domain.use_case.validation

import com.codeturtle.notes.R
import com.codeturtle.notes.authentication.registration.domain.use_case.util.ValidationResult
import com.codeturtle.notes.common.uits.UiText

class ValidatePassword {
    fun execute(password: String): ValidationResult {
        if(password.isBlank()){
            return ValidationResult(
                success = false,
                errorMessage = UiText.StringResource(R.string.the_password_can_t_be_blank)
            )
        }
        if(password.length < 8){
            return ValidationResult(
                success = false,
                errorMessage = UiText.StringResource(R.string.the_password_need_to_consist_of_at_least_8_characters)
            )
        }
        return ValidationResult(success = true)
    }
}
