package com.codeturtle.notes.notes.edit_note.presentation

import com.codeturtle.notes.common.utils.ErrorResponse
import com.codeturtle.notes.notes.edit_note.domain.model.EditNoteResponse

data class EditNoteState(
    val isLoading: Boolean = false,
    val data: EditNoteResponse? = null,
    val errorData: ErrorResponse? = null,
    val errorMessage: String = ""
)
