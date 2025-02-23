package com.codeturtle.notes.notes.notes_list.presentation

import com.codeturtle.notes.common.utils.ErrorResponse
import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem

data class NoteListState(
    val isLoading: Boolean = false,
    val data: List<NoteListResponseItem>? = null,
    val dataError: ErrorResponse? = null,
    val errorMessage: String = ""
)