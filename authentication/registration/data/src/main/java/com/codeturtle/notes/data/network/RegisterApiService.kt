package com.codeturtle.notes.data.network

import com.codeturtle.notes.common.constant.ServerUrlList.REGISTER
import com.codeturtle.notes.domain.model.RegisterRequest
import com.codeturtle.notes.domain.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {
    @POST(REGISTER)
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}