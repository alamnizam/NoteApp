package com.codeturtle.notes.notes.note_search.presentation

import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem

sealed class NoteSearchUIEvent(
    val note: NoteListResponseItem? = null
) {
    data class OnSearchQueryChanged(val query: String) : NoteSearchUIEvent()
    class OnNoteClicked(note:NoteListResponseItem?) : NoteSearchUIEvent(note = note)
    data object OnBackNavigationClicked : NoteSearchUIEvent()
}