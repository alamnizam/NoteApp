package com.codeturtle.notes.presentation

sealed class EditNoteUIEvent {
    data class OnTitleChanged(val title: String) : EditNoteUIEvent()
    data class OnDescriptionChanged(val description: String) : EditNoteUIEvent()
    data class SetHiddenId(val id: Int) : EditNoteUIEvent()
    data object OnBackNavigationClicked : EditNoteUIEvent()
    data object OnEditNoteClicked : EditNoteUIEvent()
}
