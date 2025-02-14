package com.codeturtle.notes.notes.add_note.domain.repository

import com.codeturtle.notes.notes.add_note.data.model.AddNoteRequest
import com.codeturtle.notes.notes.add_note.domain.model.AddNoteResponse
import retrofit2.Response

interface AddNoteRepository {
    suspend fun addNote(request: AddNoteRequest): Response<AddNoteResponse>
}