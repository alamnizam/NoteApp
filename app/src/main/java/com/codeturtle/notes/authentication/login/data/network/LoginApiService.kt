package com.codeturtle.notes.authentication.login.data.network

import com.codeturtle.notes.authentication.login.data.model.LoginRequest
import com.codeturtle.notes.authentication.login.domain.model.LoginResponse
import com.codeturtle.notes.common.constant.ServerUrlList.LOGIN
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST(LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
