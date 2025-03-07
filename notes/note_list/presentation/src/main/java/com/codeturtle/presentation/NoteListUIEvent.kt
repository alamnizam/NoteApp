package com.codeturtle.presentation

import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem

sealed class NoteListUIEvent(
    val note: NoteListResponseItem? = null
) {
    data object OnSearchIconClicked : NoteListUIEvent()
    data object OnLogoutIconClicked : NoteListUIEvent()
    data object OnAddNoteClicked : NoteListUIEvent()
    class OnNoteClicked(note:NoteListResponseItem?) : NoteListUIEvent(note = note)
}