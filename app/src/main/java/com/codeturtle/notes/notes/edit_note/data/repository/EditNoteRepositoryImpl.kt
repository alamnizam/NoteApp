package com.codeturtle.notes.notes.edit_note.data.repository

import com.codeturtle.notes.notes.edit_note.data.model.EditNoteRequest
import com.codeturtle.notes.notes.edit_note.data.network.EditNoteApiService
import com.codeturtle.notes.notes.edit_note.domain.model.EditNoteResponse
import com.codeturtle.notes.notes.edit_note.domain.repository.EditNoteRepository
import retrofit2.Response

class EditNoteRepositoryImpl(
    val apiService: EditNoteApiService
) : EditNoteRepository {
    override suspend fun editNote(request: EditNoteRequest): Response<EditNoteResponse> {
        return apiService.editNote(request)
    }
}