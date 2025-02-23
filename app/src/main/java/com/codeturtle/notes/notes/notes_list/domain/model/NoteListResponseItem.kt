package com.codeturtle.notes.notes.notes_list.domain.model

data class NoteListResponseItem(
    val date: Long,
    val description: String,
    val id: Int,
    val noteTitle: String
)