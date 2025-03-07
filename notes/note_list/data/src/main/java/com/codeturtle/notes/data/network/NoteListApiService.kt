package com.codeturtle.notes.data.network

import com.codeturtle.notes.common.constant.ServerUrlList.GET_NOTES
import com.codeturtle.notes.domain.model.NoteListResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface NoteListApiService {
    @GET(GET_NOTES)
    suspend fun getNoteList() : Response<List<NoteListResponseItem>>
}
