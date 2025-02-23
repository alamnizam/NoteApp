package com.codeturtle.notes.notes.notes_list.domain.repository

import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem
import retrofit2.Response

interface NoteListRepository {
    suspend fun getNoteList() : Response<List<NoteListResponseItem>>
}
