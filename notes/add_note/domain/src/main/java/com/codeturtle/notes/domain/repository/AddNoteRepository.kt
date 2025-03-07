package com.codeturtle.notes.domain.repository

import com.codeturtle.notes.domain.model.AddNoteRequest
import com.codeturtle.notes.domain.model.AddNoteResponse
import retrofit2.Response

interface AddNoteRepository {
    suspend fun addNote(request: AddNoteRequest): Response<AddNoteResponse>
}