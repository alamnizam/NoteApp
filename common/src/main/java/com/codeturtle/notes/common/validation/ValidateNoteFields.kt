package com.codeturtle.notes.common.validation

import com.codeturtle.notes.common.utils.UiText

class ValidateNoteFields(
    private val validateFieldNotEmpty: ValidateFieldNotEmpty
) {
    fun execute(title: String, description: String): NoteFieldsValidation {
        val titleResult = validateFieldNotEmpty.execute(title)
        val descriptionResult = validateFieldNotEmpty.execute(description)

        return NoteFieldsValidation(
            titleError = titleResult.errorMessage,
            descriptionError = descriptionResult.errorMessage,
            isValid = titleResult.success && descriptionResult.success
        )
    }
}

data class NoteFieldsValidation(
    val titleError: UiText?,
    val descriptionError: UiText?,
    val isValid: Boolean
)
