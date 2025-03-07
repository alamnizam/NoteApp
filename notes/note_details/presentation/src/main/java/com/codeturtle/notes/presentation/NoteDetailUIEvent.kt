package com.codeturtle.notes.presentation

import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem

sealed class NoteDetailUIEvent(
    val note: NoteListResponseItem? = null
) {
    data object OnBackNavigationClicked : NoteDetailUIEvent()
    class OnEditNoteClicked(note:NoteListResponseItem?) : NoteDetailUIEvent(note = note)
}