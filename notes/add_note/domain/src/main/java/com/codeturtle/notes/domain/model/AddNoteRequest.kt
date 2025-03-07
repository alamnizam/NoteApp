package com.codeturtle.notes.domain.model

data class AddNoteRequest(
    val date: Long,
    val description: String,
    val noteTitle: String
)