package com.codeturtle.notes.domain.repository

import com.codeturtle.notes.domain.model.DeleteNoteResponse
import retrofit2.Response

interface DeleteNoteRepository {
    suspend fun deleteNote(id: Int): Response<DeleteNoteResponse>
}