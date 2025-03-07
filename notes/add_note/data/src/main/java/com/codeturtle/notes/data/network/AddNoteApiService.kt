package com.codeturtle.notes.data.network

import com.codeturtle.notes.common.constant.ServerUrlList.ADD_NOTE
import com.codeturtle.notes.domain.model.AddNoteRequest
import com.codeturtle.notes.domain.model.AddNoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AddNoteApiService {
    @POST(ADD_NOTE)
    suspend fun addNote(@Body request: AddNoteRequest): Response<AddNoteResponse>
}