package com.codeturtle.notes.notes.add_note.presentation

import com.codeturtle.notes.common.utils.UiText

data class  AddNoteUIState(
    val title: String = "",
    val titleError: UiText? = null,
    val description: String = "",
    val descriptionError: UiText? = null
)