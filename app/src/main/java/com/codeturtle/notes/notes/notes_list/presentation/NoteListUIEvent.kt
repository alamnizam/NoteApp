package com.codeturtle.notes.notes.notes_list.presentation

sealed class NoteListUIEvent {
    data object SearchIconClicked : NoteListUIEvent()
    data object LogoutIconClicked : NoteListUIEvent()
    data object AddNoteClicked : NoteListUIEvent()
}