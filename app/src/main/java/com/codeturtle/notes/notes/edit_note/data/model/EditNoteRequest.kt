package com.codeturtle.notes.notes.edit_note.data.model

data class EditNoteRequest(
    val id: Int,
    val description: String,
    val noteTitle: String,
    val date: Long
)