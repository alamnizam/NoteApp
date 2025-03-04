package com.codeturtle.notes.notes.note_detail.presentation

sealed class NoteDetailUIEvent {
    data object OnBackNavigation : NoteDetailUIEvent()
    data object OnEditNote : NoteDetailUIEvent()
}