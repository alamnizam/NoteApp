package com.codeturtle.noets.data.repository

import com.codeturtle.noets.data.network.EditNoteApiService
import com.codeturtle.notes.domain.model.EditNoteRequest
import com.codeturtle.notes.domain.model.EditNoteResponse
import com.codeturtle.notes.domain.repository.EditNoteRepository
import retrofit2.Response

class EditNoteRepositoryImpl(
    private val apiService: EditNoteApiService
) : EditNoteRepository {
    override suspend fun editNote(request: EditNoteRequest): Response<EditNoteResponse> {
        return apiService.editNote(request)
    }
}