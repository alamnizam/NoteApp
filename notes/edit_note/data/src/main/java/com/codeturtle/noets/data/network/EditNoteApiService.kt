package com.codeturtle.noets.data.network


import com.codeturtle.notes.common.constant.ServerUrlList.UPDATE_NOTE
import com.codeturtle.notes.domain.model.EditNoteRequest
import com.codeturtle.notes.domain.model.EditNoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface EditNoteApiService {
    @POST(UPDATE_NOTE)
    suspend fun editNote(@Body request: EditNoteRequest) : Response<EditNoteResponse>
}
