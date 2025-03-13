package com.codeturtle.notes.data.network

import com.codeturtle.notes.common.constant.ServerUrlList.DELETE_NOTE
import com.codeturtle.notes.domain.model.DeleteNoteResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Query

interface DeleteNoteApiService {
    @DELETE(DELETE_NOTE)
    suspend fun deleteNote(@Query("id") id: Int): Response<DeleteNoteResponse>
}