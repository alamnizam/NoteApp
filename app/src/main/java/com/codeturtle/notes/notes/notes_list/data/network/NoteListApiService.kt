package com.codeturtle.notes.notes.notes_list.data.network

import com.codeturtle.notes.common.constant.ServerUrlList.GET_NOTES
import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface NoteListApiService {
    @GET(GET_NOTES)
    suspend fun getNoteList() : Response<List<NoteListResponseItem>>
}
