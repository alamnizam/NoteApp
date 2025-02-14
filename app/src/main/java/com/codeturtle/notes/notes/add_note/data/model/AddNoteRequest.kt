package com.codeturtle.notes.notes.add_note.data.model

data class AddNoteRequest(
    val date: Long,
    val description: String,
    val noteTitle: String
)