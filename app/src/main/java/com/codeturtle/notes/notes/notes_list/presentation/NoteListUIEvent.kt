package com.codeturtle.notes.notes.notes_list.presentation

import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem

sealed class NoteListUIEvent(
    val note: NoteListResponseItem? = null
) {
    data object SearchIconClicked : NoteListUIEvent()
    data object LogoutIconClicked : NoteListUIEvent()
    data object AddNoteClicked : NoteListUIEvent()
    class NoteClicked(note:NoteListResponseItem?) : NoteListUIEvent(note = note)
}