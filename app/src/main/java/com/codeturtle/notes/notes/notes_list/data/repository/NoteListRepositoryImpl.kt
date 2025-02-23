package com.codeturtle.notes.notes.notes_list.data.repository

import com.codeturtle.notes.notes.notes_list.data.network.NoteListApiService
import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem
import com.codeturtle.notes.notes.notes_list.domain.repository.NoteListRepository
import retrofit2.Response

class NoteListRepositoryImpl(
    private val apiService: NoteListApiService
) : NoteListRepository {
    override suspend fun getNoteList(): Response<List<NoteListResponseItem>> {
        return apiService.getNoteList()
    }
}