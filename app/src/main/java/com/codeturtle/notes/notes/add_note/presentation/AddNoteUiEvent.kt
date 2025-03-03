package com.codeturtle.notes.notes.add_note.presentation

sealed class AddNoteUiEvent {
    data class OnTitleChanged(val title: String) : AddNoteUiEvent()
    data class OnDescriptionChanged(val description: String) : AddNoteUiEvent()
    data object OnBackNavigation : AddNoteUiEvent()
    data object OnSaveNote : AddNoteUiEvent()
}