package com.codeturtle.notes.data.repository

import com.codeturtle.notes.data.network.AddNoteApiService
import com.codeturtle.notes.domain.model.AddNoteRequest
import com.codeturtle.notes.domain.model.AddNoteResponse
import com.codeturtle.notes.domain.repository.AddNoteRepository
import retrofit2.Response

class AddNoteRepositoryImpl(
    private val apiService: AddNoteApiService
) : AddNoteRepository {
    override suspend fun addNote(request: AddNoteRequest): Response<AddNoteResponse> {
        return apiService.addNote(request)
    }
}