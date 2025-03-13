package com.codeturtle.notes.presentation

import com.codeturtle.notes.common.utils.ErrorResponse
import com.codeturtle.notes.domain.model.DeleteNoteResponse

data class DeleteNoteState(
    val isLoading: Boolean = false,
    val data: DeleteNoteResponse? = null,
    val errorData: ErrorResponse? = null,
    val errorMessage: String = ""
)
