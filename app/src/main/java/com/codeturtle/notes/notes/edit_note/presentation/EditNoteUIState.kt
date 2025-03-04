package com.codeturtle.notes.notes.edit_note.presentation

import com.codeturtle.notes.common.utils.UiText

data class  EditNoteUIState(
    val title: String = "",
    val titleError: UiText? = null,
    val description: String = "",
    val descriptionError: UiText? = null
)