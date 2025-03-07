package com.codeturtle.notes.data.repository

import com.codeturtle.notes.data.network.NoteListApiService
import com.codeturtle.notes.domain.model.NoteListResponseItem
import com.codeturtle.notes.domain.repository.NoteListRepository
import retrofit2.Response

class NoteListRepositoryImpl(
    private val apiService: NoteListApiService
) : NoteListRepository {
    override suspend fun getNoteList(): Response<List<NoteListResponseItem>> {
        return apiService.getNoteList()
    }
}