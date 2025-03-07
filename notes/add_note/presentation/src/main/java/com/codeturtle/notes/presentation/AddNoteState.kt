package com.codeturtle.notes.presentation

import com.codeturtle.notes.common.utils.ErrorResponse
import com.codeturtle.notes.domain.model.AddNoteResponse

data class AddNoteState(
    val isLoading: Boolean = false,
    val data: AddNoteResponse? = null,
    val errorData: ErrorResponse? = null,
    val errorMessage: String = ""
)
