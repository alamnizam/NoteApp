package com.codeturtle.notes.notes.add_note.presentation

sealed class AddNoteUIEvent {
    data class OnTitleChanged(val title: String) : AddNoteUIEvent()
    data class OnDescriptionChanged(val description: String) : AddNoteUIEvent()
    data object OnBackNavigation : AddNoteUIEvent()
    data object OnSaveNote : AddNoteUIEvent()
}