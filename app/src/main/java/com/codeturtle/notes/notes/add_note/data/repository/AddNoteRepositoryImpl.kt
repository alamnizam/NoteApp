package com.codeturtle.notes.notes.add_note.data.repository

import com.codeturtle.notes.notes.add_note.data.model.AddNoteRequest
import com.codeturtle.notes.notes.add_note.data.network.AddNoteApiService
import com.codeturtle.notes.notes.add_note.domain.model.AddNoteResponse
import com.codeturtle.notes.notes.add_note.domain.repository.AddNoteRepository
import retrofit2.Response

class AddNoteRepositoryImpl(
    private val apiService: AddNoteApiService
) : AddNoteRepository {
    override suspend fun addNote(request: AddNoteRequest): Response<AddNoteResponse> {
        return apiService.addNote(request)
    }
}