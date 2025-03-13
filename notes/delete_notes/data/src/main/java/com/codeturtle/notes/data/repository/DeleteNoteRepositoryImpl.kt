package com.codeturtle.notes.data.repository

import com.codeturtle.notes.data.network.DeleteNoteApiService
import com.codeturtle.notes.domain.model.DeleteNoteResponse
import com.codeturtle.notes.domain.repository.DeleteNoteRepository
import retrofit2.Response

class DeleteNoteRepositoryImpl(
    private val apiService: DeleteNoteApiService
) : DeleteNoteRepository {
    override suspend fun deleteNote(id: Int): Response<DeleteNoteResponse> {
        return apiService.deleteNote(id)
    }
}