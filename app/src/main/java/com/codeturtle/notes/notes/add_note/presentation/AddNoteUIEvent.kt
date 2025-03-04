package com.codeturtle.notes.notes.add_note.presentation

sealed class AddNoteUIEvent {
    data class OnTitleChanged(val title: String) : AddNoteUIEvent()
    data class OnDescriptionChanged(val description: String) : AddNoteUIEvent()
    data object OnBackNavigationClicked : AddNoteUIEvent()
    data object OnSaveNoteClicked : AddNoteUIEvent()
}