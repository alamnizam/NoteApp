package com.codeturtle.notes.domain.model

data class EditNoteRequest(
    val id: Int,
    val description: String,
    val noteTitle: String,
    val date: Long
)