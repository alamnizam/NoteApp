package com.codeturtle.notes.common.validation

import com.codeturtle.notes.common.R
import com.codeturtle.notes.common.utils.UiText
import com.codeturtle.notes.common.utils.ValidationResult

class ValidateFieldNotEmpty {
    fun execute(fieldData: String): ValidationResult {
        return when {
            fieldData.isBlank() -> ValidationResult(
                success = false,
                errorMessage = UiText.StringResource(R.string.this_field_cannot_be_empty)
            )

            else -> ValidationResult(success = true)
        }
    }
}