package com.codeturtle.notes.data.network

import com.codeturtle.notes.common.constant.ServerUrlList.LOGIN
import com.codeturtle.notes.domain.model.LoginRequest
import com.codeturtle.notes.domain.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST(LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
