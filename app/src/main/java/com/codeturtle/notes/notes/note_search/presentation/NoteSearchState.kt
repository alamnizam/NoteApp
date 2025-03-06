package com.codeturtle.notes.notes.note_search.presentation

import com.codeturtle.notes.common.utils.ErrorResponse
import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem

data class NoteSearchState(
    val isLoading: Boolean = false,
    val dataError: ErrorResponse? = null,
    val errorMessage: String = "",
    val data: List<NoteListResponseItem>? = null
)