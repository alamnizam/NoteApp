package com.codeturtle.notes.domain.repository

import com.codeturtle.notes.domain.model.EditNoteRequest
import com.codeturtle.notes.domain.model.EditNoteResponse
import retrofit2.Response

interface EditNoteRepository {
    suspend fun editNote(request: EditNoteRequest): Response<EditNoteResponse>
}