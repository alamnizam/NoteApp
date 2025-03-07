package com.codeturtle.notes.presentation

import com.codeturtle.notes.common.utils.ErrorResponse
import com.codeturtle.notes.domain.model.NoteListResponseItem

data class NoteListState(
    val isLoading: Boolean = false,
    val data: List<NoteListResponseItem>? = null,
    val dataError: ErrorResponse? = null,
    val errorMessage: String = ""
)