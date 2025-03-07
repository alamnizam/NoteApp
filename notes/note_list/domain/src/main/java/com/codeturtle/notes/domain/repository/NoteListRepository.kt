package com.codeturtle.notes.domain.repository

import com.codeturtle.notes.domain.model.NoteListResponseItem
import retrofit2.Response

interface NoteListRepository {
    suspend fun getNoteList() : Response<List<NoteListResponseItem>>
}
