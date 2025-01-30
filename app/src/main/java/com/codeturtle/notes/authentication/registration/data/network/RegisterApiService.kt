package com.codeturtle.notes.authentication.registration.data.network

import com.codeturtle.notes.authentication.registration.data.model.RegisterRequest
import com.codeturtle.notes.authentication.registration.data.model.RegisterResponse
import com.codeturtle.notes.common.constant.ServerUrlList.REGISTER
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {
    @POST(REGISTER)
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}