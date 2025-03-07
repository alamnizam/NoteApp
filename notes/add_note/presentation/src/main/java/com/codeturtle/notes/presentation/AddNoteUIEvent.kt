package com.codeturtle.notes.presentation

sealed class AddNoteUIEvent {
    data class OnTitleChanged(val title: String) : AddNoteUIEvent()
    data class OnDescriptionChanged(val description: String) : AddNoteUIEvent()
    data object OnBackNavigationClicked : AddNoteUIEvent()
    data object OnSaveNoteClicked : AddNoteUIEvent()
}