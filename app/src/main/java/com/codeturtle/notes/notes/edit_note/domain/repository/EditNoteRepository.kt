package com.codeturtle.notes.notes.edit_note.domain.repository

import com.codeturtle.notes.notes.edit_note.data.model.EditNoteRequest
import com.codeturtle.notes.notes.edit_note.domain.model.EditNoteResponse
import retrofit2.Response

interface EditNoteRepository {
    suspend fun editNote(request: EditNoteRequest): Response<EditNoteResponse>
}