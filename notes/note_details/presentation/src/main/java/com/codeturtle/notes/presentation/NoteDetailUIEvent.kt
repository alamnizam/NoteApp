package com.codeturtle.notes.presentation

import com.codeturtle.notes.domain.model.NoteListResponseItem

sealed class NoteDetailUIEvent(
    val id: Int? = null,
    val note: NoteListResponseItem? = null
) {
    data object OnBackNavigationClicked : NoteDetailUIEvent()
    class OnEditNoteClicked(note:NoteListResponseItem?) : NoteDetailUIEvent(note = note)
    class OnDeleteNoteClicked(id:Int?) : NoteDetailUIEvent(id = id)
}