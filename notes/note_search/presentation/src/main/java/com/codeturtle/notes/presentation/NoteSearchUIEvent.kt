package com.codeturtle.notes.presentation

import com.codeturtle.notes.domain.model.NoteListResponseItem

sealed class NoteSearchUIEvent(
    val note: NoteListResponseItem? = null
) {
    class OnNoteClicked(note:NoteListResponseItem?) : NoteSearchUIEvent(note = note)
    data class OnSearchQueryChanged(val query: String) : NoteSearchUIEvent()
    data object OnBackNavigationClicked : NoteSearchUIEvent()
}